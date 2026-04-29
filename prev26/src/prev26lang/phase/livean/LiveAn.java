package prev26lang.phase.livean;

import java.util.*;

import prev26lang.phase.asmgen.*;
import prev26lang.phase.*;

/**
 * Liveness analysis phase.
 */
public class LiveAn extends Phase {

	/** Liveness information of all analyzed code chunks. */
	private final Vector<LIV.CodeChunkAnal> codeChunkAnalyses;

	/**
	 * Phase construction.
	 */
	public LiveAn() {
		super("livean");
		codeChunkAnalyses = new Vector<LIV.CodeChunkAnal>();
	}

	/**
	 * Performs liveness analysis of all assembly-code chunks.
	 * 
	 * @param codeChunks The code chunks to analyze.
	 */
	public void analyze(final Vector<ASM.CodeChunk> codeChunks) {
		codeChunkAnalyses.clear();

		for (final ASM.CodeChunk codeChunk : codeChunks) {
			final FlowGraph flowGraph = new FlowGraph(codeChunk);
			final LiveAnalyzer analyzer = new LiveAnalyzer(flowGraph.instructions());
			codeChunkAnalyses.add(analyzer.analyze(flowGraph));
		}
	}

	/**
	 * Returns a defensive copy of all code-chunk analyses.
	 */
	public Vector<LIV.CodeChunkAnal> codeChunkAnalyses() {
		return new Vector<LIV.CodeChunkAnal>(codeChunkAnalyses);
	}

}
