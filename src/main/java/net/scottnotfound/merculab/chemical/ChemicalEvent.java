package net.scottnotfound.merculab.chemical;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChemicalEvent extends Event
{
    private final ChemicalStack chemical;
    private final World world;
    private final BlockPos pos;

    public ChemicalEvent(ChemicalStack chemical, World world, BlockPos pos)
    {
        this.chemical = chemical;
        this.world = world;
        this.pos = pos;
    }

    public ChemicalStack getChemical()
    {
        return chemical;
    }

    public World getWorld()
    {
        return world;
    }

    public BlockPos getPos()
    {
        return pos;
    }

    /** Fire this when chemicals are moved. */
    public static class ChemicalMotionEvent extends ChemicalEvent
    {
        public ChemicalMotionEvent(ChemicalStack chemical, World world, BlockPos pos)
        {
            super(chemical, world, pos);
        }
    }

    /** Fire this when chemicals are inserted. */
    public static class ChemicalInsertionEvent extends ChemicalEvent
    {
        private final IChemicalContainer container;
        private final int amount;

        public ChemicalInsertionEvent(ChemicalStack chemical, World world, BlockPos pos, IChemicalContainer container, int amount)
        {
            super(chemical, world, pos);
            this.container = container;
            this.amount = amount;
        }

        public IChemicalContainer getContainer()
        {
            return container;
        }

        public int getAmount()
        {
            return amount;
        }
    }

    /** Fire this when chemicals are extracted. */
    public static class ChemicalExtractionEvent extends ChemicalEvent
    {
        private final IChemicalContainer container;
        private final int amount;

        public ChemicalExtractionEvent(ChemicalStack chemical, World world, BlockPos pos, IChemicalContainer container, int amount)
        {
            super(chemical, world, pos);
            this.container = container;
            this.amount = amount;
        }

        public IChemicalContainer getContainer()
        {
            return container;
        }

        public int getAmount()
        {
            return amount;
        }
    }

    /** Shortcut for firing the chemical events. */
    public static final void fireEvent(ChemicalEvent event)
    {
        MinecraftForge.EVENT_BUS.post(event);
    }

}
