
public class Matrix {
	
	double[][] values;

	public Matrix(double[][] values) {
		this.values = values;
	}
	
	public int getNbRows() {
		return values.length;
	}
	
	public int getNbColumns() {
		return values[0].length;
	}
	
	public void setEntry(int row, int column, double value) {
		if(row < getNbRows() && row >= 0 && column < getNbColumns() && column >= 0)
			values[row][column] = value;
		else
			throw new RuntimeException("Out of Matrix bounds");
	}
	
	public double getEntry(int row, int column) {
		if(row < getNbRows() && row >= 0 && column < getNbColumns() && column >= 0)
			return values[row][column];
		else
			throw new RuntimeException("Out of Matrix bounds");
	}
	
	public Matrix transpose() {
		double[][] newVals = new double[getNbColumns()][getNbRows()];
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < getNbColumns(); j++) {
				newVals[j][i] = values[i][j];
			}
		}
		
		return new Matrix(newVals);
	}
	
	public Matrix add(Matrix m) {
		if(getNbRows() != m.getNbRows() || getNbColumns() != m.getNbColumns())
			throw new RuntimeException("Matrix sizes mismatch");
		
		double[][] newVals = new double[getNbRows()][getNbColumns()];
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < getNbColumns(); j++) {
				newVals[i][j] = values[i][j] + m.getEntry(i, j);
			}
		}
		
		return new Matrix(newVals);
	}
	
	public Matrix subtract(Matrix m) {
		if(getNbRows() != m.getNbRows() || getNbColumns() != m.getNbColumns())
			throw new RuntimeException("Matrix sizes mismatch");
		
		double[][] newVals = new double[getNbRows()][getNbColumns()];
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < getNbColumns(); j++) {
				newVals[i][j] = values[i][j] - m.getEntry(i, j);
			}
		}
		
		return new Matrix(newVals);
	}
	
	public double getMultipliedEntry(Matrix m, int row, int column) {
		if(row >= getNbRows() || row < 0 || column >= getNbColumns() || column < 0)
			throw new RuntimeException("Out of Matrix bounds");
		
		double sum = 0;
		
		for(int i = 0; i < getNbColumns(); i++) {
			sum += values[row][i] * m.getEntry(i, column);
		}
		
		return sum;
	}
	
	public Matrix multiply(double scalar) { //Scalar multiple
		double[][] newVals = new double[getNbRows()][getNbColumns()];
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < getNbColumns(); j++) {
				newVals[i][j] = scalar * values[i][j];
			}
		}
		
		return new Matrix(newVals);
	}
	
	public Matrix multiply(Matrix m) {
		if(getNbColumns() != m.getNbRows())
			throw new RuntimeException("Matrix sizes mismatch");
		
		double[][] newVals = new double[getNbRows()][m.getNbColumns()];
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < m.getNbColumns(); j++) {
				newVals[i][j] = getMultipliedEntry(m, i, j);
			}
		}
		
		return new Matrix(newVals);
	}
	
	public static Matrix getIdentity(int size) {
		double[][] newVals = new double[size][size];
		
		for(int i = 0; i < size; i++) {
			newVals[i][i] = 1; //Put 1 everywhere on the diagonal
		}
		
		return new Matrix(newVals);
	}
	
	public static Matrix getZero(int size) {
		return new Matrix(new double[size][size]);
	}
	
	public boolean equals(Matrix m) {
		if(getNbRows() != m.getNbRows() || getNbColumns() != m.getNbColumns())
			return false;
		
		for(int i = 0; i < getNbRows(); i++) {
			for(int j = 0; j < getNbColumns(); j++) {
				if(values[i][j] != m.getEntry(i, j))
					return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		String str = "[";
		
		for(int i = 0; i < getNbRows(); i++) {
			str += "{";
			for(int j = 0; j < getNbColumns(); j++) {
				str += values[i][j];
				if(j != getNbColumns() - 1)
					str += ", ";
			}
			str += "}";
			if(i != getNbRows() - 1)
				str += ",\n";
		}
		
		str += "]";
		
		return str;
	}
}
