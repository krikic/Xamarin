package eti.domino;

import java.util.ArrayList;
import java.util.Random;

public class Table {
	private ArrayList<DominoPiece> humanPlayerPieces;
	private ArrayList<DominoPiece> computerPlayerPieces;
	private ArrayList<DominoPiece> bankPieces;
	private ArrayList<DominoPiece> tablePieces;

	public Table() {
		humanPlayerPieces = new ArrayList<DominoPiece>();
		computerPlayerPieces = new ArrayList<DominoPiece>();
		bankPieces = new ArrayList<DominoPiece>();
		tablePieces = new ArrayList<DominoPiece>();

		for (int top=0; top <= 6; top++) {
			for (int bottom=top; bottom <= 6; bottom++) {
				bankPieces.add(new DominoPiece(top, bottom));
			}
		}
	}

	public ArrayList<DominoPiece> getHumanPlayerPieces() {
		return humanPlayerPieces;
	}

	public ArrayList<DominoPiece> getComputerPlayerPieces() {
		return computerPlayerPieces;
	}

	public ArrayList<DominoPiece> getTablePieces() {
		return tablePieces;
	}
	
	public void startGame() {
		try {
			for (int i=1; i <= 7; i++) {
				getRandomPieceForHuman();
			}
			for (int i=1; i <= 7; i++) {
				getRandomPieceForComputer();
			}
			putRandomPieceOnTable();
		} catch (NoPiecesLeftException e) {
			;
		}
	}

	public void putRandomPieceOnTable() throws NoPiecesLeftException {
		int idx = getRandomPieceIndex();
		DominoPiece piece = bankPieces.remove(idx);
		tablePieces.add(piece);
	}

	// Return true when the piece has been successfully placed on table.
	public boolean putPieceOnTable(DominoPiece piece, DominoPiece adjacentPiece) {
		if (piece.fitsWith(adjacentPiece.topDots) && adjacentPiece.topFree) {
			movePieceToTable(piece);
			piece.setPosition(adjacentPiece.getPositionOnePieceHigher());
			if (piece.topDots == adjacentPiece.topDots) {
				piece.flip();
			}
			adjacentPiece.topFree = false;
			piece.bottomFree = false;
			return true;
		} else if (piece.fitsWith(adjacentPiece.bottomDots) && adjacentPiece.bottomFree) {
			movePieceToTable(piece);
			piece.setPosition(adjacentPiece.getPositionOnePieceLower());
			if (piece.bottomDots == adjacentPiece.bottomDots) {
				piece.flip();
			}
			adjacentPiece.bottomFree = false;
			piece.topFree = false;
			return true;
		}
		return false;
	}

	public DominoPiece getRandomPieceForHuman() throws NoPiecesLeftException {
		int idx = getRandomPieceIndex();
		DominoPiece piece = bankPieces.remove(idx);
		humanPlayerPieces.add(piece);
		return piece;
	}

	public DominoPiece getRandomPieceForComputer() throws NoPiecesLeftException {
		int idx = getRandomPieceIndex();
		DominoPiece piece = bankPieces.remove(idx);
		computerPlayerPieces.add(piece);
		return piece;
	}
	
	public void drawPiecesForPlayerIfNeeded() throws NoPiecesLeftException {
		while (!humanHasMoves()) {
			// We have to draw, so get a random piece and see if the player
			// needs another one.
			getRandomPieceForHuman();
		}
	}
	
	public boolean computerHasWon() {
		return computerPlayerPieces.isEmpty();
	}
	
	public boolean humanHasWon() {
		return humanPlayerPieces.isEmpty();
	}
	
	public boolean isTieWithNoComputerMoves() {
		return bankPieces.isEmpty() && !humanHasMoves();
	}

	private int getRandomPieceIndex() throws NoPiecesLeftException {
		if (bankPieces.isEmpty()) {
			throw new NoPiecesLeftException();
		}
		Random generator = new Random();
		return generator.nextInt(bankPieces.size());
	}

	private void movePieceToTable(DominoPiece piece) {
		if (humanPlayerPieces.contains(piece)) {
			humanPlayerPieces.remove(piece);
		} else if (computerPlayerPieces.contains(piece)) {
			computerPlayerPieces.remove(piece);
		}
		tablePieces.add(piece);
	}

	private boolean humanHasMoves() {
		for (DominoPiece humanPiece : humanPlayerPieces) {
			for (DominoPiece tablePiece : tablePieces) {
				if ((humanPiece.fitsWith(tablePiece.topDots) && tablePiece.topFree)
						|| (humanPiece.fitsWith(tablePiece.bottomDots) && tablePiece.bottomFree)) {
					return true;
				}
			}
		}
		return false;
	}	
}
