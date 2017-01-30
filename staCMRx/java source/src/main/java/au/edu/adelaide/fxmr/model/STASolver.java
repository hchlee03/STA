package au.edu.adelaide.fxmr.model;

import au.edu.adelaide.fxmr.model.mr.MRProblem;
import au.edu.adelaide.fxmr.model.mr.MRSolution;
import au.edu.adelaide.fxmr.model.mr.MRSolver;
import au.edu.adelaide.fxmr.model.mr.MRSolverAJOptimiser;
import cern.colt.matrix.DoubleMatrix2D;

/**
 * Very basic STA MR Solver (without coupling)
 * 
 * Extends Single threaded CMRSolver for use as a dropin replacement for
 * CMRxfits
 * 
 * @author luke
 */

public class STASolver extends CMRxSolver {
	public STASolver() {
	}

	/**
	 * Override the most parameterised function and ignore all but the first parameter
	 */
	public CMRSolution solve(CMRxProblem problem, SolverListener sl, boolean targetSet, double target) {
		long start = System.nanoTime();

		int nvar = problem.getNVar();
		double[][] means = problem.getMeans();
		DoubleMatrix2D[] weights = problem.getWeights();

		double[][] xBar = null;

		MRSolver mrSolver = new MRSolverAJOptimiser();

		// Add first CMRTrial

		xBar = new double[nvar][];
		double fBar = 0;
		for (int i = 0; i < nvar; i++) {
			MRProblem p = new MRProblem(means[i], weights[i], problem.getAdj()[i]);
			MRSolution sol = mrSolver.solve(p);
			xBar[i] = sol.getxVector();
			fBar += sol.getfVal();
		}

		return new CMRSolution(fBar, xBar, null, (double) (System.nanoTime() - start) / 1_000_000_000, null,
				mrSolver.getCalls(), 0);
	}
}
