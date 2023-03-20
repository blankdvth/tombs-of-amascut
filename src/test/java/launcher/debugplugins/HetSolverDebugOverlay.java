package launcher.debugplugins;

import com.duckblade.osrs.toa.features.het.solver.HetSolution;
import com.duckblade.osrs.toa.features.het.solver.HetSolver;
import com.duckblade.osrs.toa.util.RaidRoom;
import com.duckblade.osrs.toa.util.RaidStateTracker;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Singleton
public class HetSolverDebugOverlay extends OverlayPanel
{

	@Inject
	private HetSolver hetSolver;

	@Inject
	private RaidStateTracker raidStateTracker;

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (raidStateTracker.getCurrentState().getCurrentRoom() != RaidRoom.HET)
		{
			return null;
		}

		getPanelComponent()
			.getChildren()
			.add(TitleComponent.builder()
				.text("Het Debug")
				.build());

		Point puzzleBase = hetSolver.getPuzzleBase();
		addLine("Base:", puzzleBase == null ? "?, ?" : puzzleBase.getX() + ", " + puzzleBase.getY());

		HetSolution solution = hetSolver.getSolution();
		addLine("Solution:", solution == null ? "null" : solution.name());

		for (HetSolution sol : HetSolution.values())
		{
			if (sol.getLastScore() != null)
			{
				addLine(sol.name(), sol.getLastScore().getOverlayString());
			}
		}

		return super.render(graphics);
	}

	private void addLine(String left, String right)
	{
		getPanelComponent()
			.getChildren()
			.add(LineComponent.builder()
				.left(left)
				.right(right)
				.build());
	}
}
