package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.lax1dude.eaglercraft.internal.vfs2.VFile2;

/**
 * TeaVM save converter backed by VFile2.
 * Persists worlds in the browser filesystem (IndexedDB).
 */
public class AnvilSaveConverter implements ISaveFormat {

    private final VFile2 savesDir;

    public AnvilSaveConverter(File baseDir) {
        this.savesDir = new VFile2(baseDir);
    }

    @Override
    public ISaveHandler getSaveLoader(String name, boolean store) {
        return new VFileSaveHandler(this.savesDir, name, store);
    }

    @Override
    public List getSaveList() {
        List<VFile2> files = this.savesDir.listFiles(true);
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        String root = this.savesDir.getPath();
        Set<String> worldNames = new HashSet<>();

        for (int i = 0, l = files.size(); i < l; ++i) {
            VFile2 f = files.get(i);
            String path = f != null ? f.getPath() : null;
            if (path == null) {
                continue;
            }
            if (root != null && path.startsWith(root)) {
                path = path.substring(root.length());
            }
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (!path.endsWith("level.dat")) {
                continue;
            }
            int slash = path.indexOf('/');
            if (slash > 0) {
                worldNames.add(path.substring(0, slash));
            }
        }

        if (worldNames.isEmpty()) {
            return Collections.emptyList();
        }

        ArrayList list = new ArrayList(worldNames.size());
        for (String name : worldNames) {
            WorldInfo info = new VFileSaveHandler(this.savesDir, name, false).loadWorldInfo();
            if (info == null) {
                continue;
            }

            String displayName = info.getWorldName();
            if (displayName == null || MathHelper.stringNullOrLengthZero(displayName)) {
                displayName = name;
            }

            list.add(new SaveFormatComparator(
                    name,
                    displayName,
                    info.getLastTimePlayed(),
                    0L,
                    info.getGameType(),
                    false,
                    info.isHardcoreModeEnabled(),
                    info.areCommandsAllowed()
            ));
        }

        Collections.sort(list);
        return list;
    }

    @Override
    public void flushCache() {
        // No-op for TeaVM.
    }

    @Override
    public WorldInfo getWorldInfo(String name) {
        return new VFileSaveHandler(this.savesDir, name, false).loadWorldInfo();
    }

    @Override
    public void deleteWorldDirectory(String name) {
        VFile2 worldDir = new VFile2(this.savesDir, name);
        List<VFile2> files = worldDir.listFiles(true);
        for (int i = 0, l = files.size(); i < l; ++i) {
            VFile2 f = files.get(i);
            if (f != null) {
                f.delete();
            }
        }
    }

    @Override
    public void renameWorld(String name, String newName) {
        VFile2 oldDir = new VFile2(this.savesDir, name);
        VFile2 newDir = new VFile2(this.savesDir, newName);
        List<VFile2> files = oldDir.listFiles(true);
        for (int i = 0, l = files.size(); i < l; ++i) {
            VFile2 f = files.get(i);
            if (f == null) {
                continue;
            }
            String path = f.getPath();
            if (path == null) {
                continue;
            }
            String oldRoot = oldDir.getPath();
            if (oldRoot != null && path.startsWith(oldRoot)) {
                String rel = path.substring(oldRoot.length());
                if (rel.startsWith("/")) {
                    rel = rel.substring(1);
                }
                VFile2 target = new VFile2(newDir, rel);
                f.renameTo(target);
            }
        }

        WorldInfo info = getWorldInfo(newName);
        if (info != null) {
            info.setWorldName(newName);
            new VFileSaveHandler(this.savesDir, newName, true).saveWorldInfo(info);
        }
    }

    @Override
    public boolean isOldMapFormat(String name) {
        return false;
    }

    @Override
    public boolean convertMapFormat(String name, IProgressUpdate progress) {
        return false;
    }

    public static class VFileSaveHandler implements ISaveHandler, IPlayerFileData {
        private final VFile2 saveDirectory;
        private final VFile2 playersDirectory;
        private final VFile2 mapDataDir;
        private final long initializationTime = System.currentTimeMillis();
        private final String saveDirectoryName;

        VFileSaveHandler(VFile2 savesRoot, String saveName, boolean createPlayers) {
            this.saveDirectory = new VFile2(savesRoot, saveName);
            this.playersDirectory = new VFile2(this.saveDirectory, "players");
            this.mapDataDir = new VFile2(this.saveDirectory, "data");
            this.saveDirectoryName = saveName;
            if (createPlayers) {
                this.playersDirectory.dirExists();
            }
            this.mapDataDir.dirExists();
            this.setSessionLock();
        }

        private void setSessionLock() {
            try (DataOutputStream out = new DataOutputStream(new VFile2(this.saveDirectory, "session.lock").getOutputStream())) {
                out.writeLong(this.initializationTime);
            } catch (IOException e) {
                throw new RuntimeException("Failed to check session lock, aborting", e);
            }
        }

        @Override
        public WorldInfo loadWorldInfo() {
            VFile2 levelDat = new VFile2(this.saveDirectory, "level.dat");
            VFile2 levelDatOld = new VFile2(this.saveDirectory, "level.dat_old");

            if (levelDat.exists()) {
                try (InputStream is = levelDat.getInputStream()) {
                    NBTTagCompound root = CompressedStreamTools.readCompressed(is);
                    return new WorldInfo(root.getCompoundTag("Data"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (levelDatOld.exists()) {
                try (InputStream is = levelDatOld.getInputStream()) {
                    NBTTagCompound root = CompressedStreamTools.readCompressed(is);
                    return new WorldInfo(root.getCompoundTag("Data"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        public void checkSessionLock() throws MinecraftException {
            VFile2 lock = new VFile2(this.saveDirectory, "session.lock");
            if (!lock.exists()) {
                setSessionLock();
                return;
            }
            try (DataInputStream in = new DataInputStream(lock.getInputStream())) {
                if (in.readLong() != this.initializationTime) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            } catch (IOException e) {
                throw new MinecraftException("Failed to check session lock, aborting");
            }
        }

        @Override
        public IChunkLoader getChunkLoader(WorldProvider provider) {
            if (provider instanceof WorldProviderHell) {
                return new AnvilChunkLoader(new VFile2(this.saveDirectory, "DIM-1"));
            } else if (provider instanceof WorldProviderEnd) {
                return new AnvilChunkLoader(new VFile2(this.saveDirectory, "DIM1"));
            } else {
                return new AnvilChunkLoader(this.saveDirectory);
            }
        }

        @Override
        public void saveWorldInfoWithPlayer(WorldInfo info, NBTTagCompound playerTag) {
            info.setSaveVersion(19133);
            NBTTagCompound data = info.cloneNBTCompound(playerTag);
            NBTTagCompound root = new NBTTagCompound();
            root.setTag("Data", data);
            writeWorld(root);
        }

        @Override
        public void saveWorldInfo(WorldInfo info) {
            NBTTagCompound data = info.getNBTTagCompound();
            NBTTagCompound root = new NBTTagCompound();
            root.setTag("Data", data);
            writeWorld(root);
        }

        private void writeWorld(NBTTagCompound root) {
            try {
                VFile2 levelNew = new VFile2(this.saveDirectory, "level.dat_new");
                VFile2 levelOld = new VFile2(this.saveDirectory, "level.dat_old");
                VFile2 level = new VFile2(this.saveDirectory, "level.dat");

                try (OutputStream os = levelNew.getOutputStream()) {
                    CompressedStreamTools.writeCompressed(root, os);
                }

                if (levelOld.exists()) {
                    levelOld.delete();
                }
                if (level.exists()) {
                    level.renameTo(levelOld);
                }
                if (level.exists()) {
                    level.delete();
                }
                levelNew.renameTo(level);
                if (levelNew.exists()) {
                    levelNew.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public IPlayerFileData getSaveHandler() {
            return this;
        }

        @Override
        public void flush() {
            try {
                ThreadedFileIOBase.threadedIOInstance.waitForFinish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public File getMapFileFromName(String name) {
            return null;
        }

        VFile2 getMapFile(String name) {
            return new VFile2(this.mapDataDir, name + ".dat");
        }

        @Override
        public String getSaveDirectoryName() {
            return this.saveDirectoryName;
        }

        @Override
        public void writePlayerData(EntityPlayer player) {
            try {
                NBTTagCompound tag = new NBTTagCompound();
                player.writeToNBT(tag);
                VFile2 tmp = new VFile2(this.playersDirectory, player.username + ".dat.tmp");
                VFile2 dat = new VFile2(this.playersDirectory, player.username + ".dat");
                try (OutputStream os = tmp.getOutputStream()) {
                    CompressedStreamTools.writeCompressed(tag, os);
                }
                if (dat.exists()) {
                    dat.delete();
                }
                tmp.renameTo(dat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void readPlayerData(EntityPlayer player) {
            VFile2 dat = new VFile2(this.playersDirectory, player.username + ".dat");
            if (!dat.exists()) {
                return;
            }
            try (InputStream is = dat.getInputStream()) {
                NBTTagCompound tag = CompressedStreamTools.readCompressed(is);
                player.readFromNBT(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String[] getAvailablePlayerDat() {
            List<VFile2> files = this.playersDirectory.listFiles(false);
            if (files == null || files.isEmpty()) {
                return new String[0];
            }
            ArrayList<String> names = new ArrayList<>();
            for (int i = 0, l = files.size(); i < l; ++i) {
                VFile2 f = files.get(i);
                if (f == null) {
                    continue;
                }
                String name = f.getName();
                if (name != null && name.endsWith(".dat")) {
                    names.add(name.substring(0, name.length() - 4));
                }
            }
            return names.toArray(new String[names.size()]);
        }
    }
}
