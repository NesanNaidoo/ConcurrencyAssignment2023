package clubSimulation;

import java.util.concurrent.atomic.AtomicInteger;

// GridBlock class to represent a block in the club.
// only one thread at a time "owns" a GridBlock

public class GridBlock {
	private int isOccupied;
	private final boolean isExit; // is tthis the exit door?
	private final boolean isBar; // is it a bar block?
	private final boolean isDance; // is it the dance area?
	private int[] coords; // the coordinate of the block.

	private final Object entranceLock = new Object(); // Lock for entrance door
	private final Object exitLock = new Object(); // Lock for exit door
	private final Object occupancyLock = new Object(); // Lock for occupancy check

	GridBlock(boolean exitBlock, boolean barBlock, boolean danceBlock) throws InterruptedException {
		isExit = exitBlock;
		isBar = barBlock;
		isDance = danceBlock;
		isOccupied = -1;
	}

	GridBlock(int x, int y, boolean exitBlock, boolean refreshBlock, boolean danceBlock) throws InterruptedException {
		this(exitBlock, refreshBlock, danceBlock);
		coords = new int[] { x, y };
	}

	public int getX() {
		return coords[0];
	}

	public int getY() {
		return coords[1];
	}

	public boolean get(int threadID) throws InterruptedException {
		// synchronized access to entrance or exit
		synchronized (isExit ? exitLock : entranceLock) {

			if (isOccupied == threadID)
				return true; // thread Already in this block
			if (isOccupied >= 0)
				return false; // space is occupied
			isOccupied = threadID; // set ID to thread that had block
			return true;

		}
	}

	// synchronized access to release method
	public void release() {
		synchronized (isExit ? exitLock : entranceLock) {
			isOccupied = -1;
		}
	}

	public boolean occupied() {
		if (isOccupied == -1)
			return false;
		return true;
	}

	// Check if the grid block is occupied by another patron // this is synchronised
	// as well
	public boolean isOccupiedByOther(int threadID) {
		synchronized (occupancyLock) {
			return isOccupied != -1 && isOccupied != threadID;
		}
	}

	public boolean isExit() {
		return isExit;
	}

	public boolean isBar() {
		return isBar;
	}

	public boolean isDanceFloor() {
		return isDance;
	}

}
