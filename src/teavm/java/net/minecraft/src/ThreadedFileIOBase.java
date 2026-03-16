package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.browser.Window;

/**
 * TeaVM implementation: use short scheduled slices instead of a tight background loop.
 */
public class ThreadedFileIOBase {
	public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();

	private final List<IThreadedFileIO> threadedIOQueue = new ArrayList<>();
	private volatile long writeQueuedCounter = 0L;
	private volatile long savedIOCounter = 0L;
	private boolean scheduled = false;

	private ThreadedFileIOBase() {
	}

	private void schedule() {
		if (scheduled) {
			return;
		}
		scheduled = true;
		Window.setTimeout(() -> processQueueSlice(), 0);
	}

	private void processQueueSlice() {
		scheduled = false;
		if (threadedIOQueue.isEmpty()) {
			return;
		}

		IThreadedFileIO io = threadedIOQueue.get(0);
		boolean keep = io.writeNextIO();

		if (!keep) {
			threadedIOQueue.remove(0);
			++savedIOCounter;
		}

		if (!threadedIOQueue.isEmpty()) {
			schedule();
		}
	}

	public void queueIO(IThreadedFileIO par1IThreadedFileIO) {
		if (!this.threadedIOQueue.contains(par1IThreadedFileIO)) {
			++this.writeQueuedCounter;
			this.threadedIOQueue.add(par1IThreadedFileIO);
			schedule();
		}
	}

	public void waitForFinish() throws InterruptedException {
		while (this.writeQueuedCounter != this.savedIOCounter) {
			if (!this.threadedIOQueue.isEmpty()) {
				IThreadedFileIO io = this.threadedIOQueue.get(0);
				boolean keep = io.writeNextIO();
				if (!keep) {
					this.threadedIOQueue.remove(0);
					++this.savedIOCounter;
				}
			} else {
				break;
			}
		}
	}
}
