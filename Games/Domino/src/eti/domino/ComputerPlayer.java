package eti.domino;

public class ComputerPlayer {
	public boolean move(Table table) {
		for (DominoPiece myPiece : table.getComputerPlayerPieces()) {
			if (put(table, myPiece)) {
				return true;
			}
		}
		// No moves! Draw and try again.
		try {
			while (!put(table, table.getRandomPieceForComputer())) {
				;
			}
		} catch (NoPiecesLeftException e) {
			return false;
		}
		return true;
	}

	private boolean put(Table table, DominoPiece myPiece) {
		// Brute force.
		for (DominoPiece tablePiece : table.getTablePieces()) {
			if (table.putPieceOnTable(myPiece, tablePiece)) {
				return true;
			}
		}
		return false;
	}
}
