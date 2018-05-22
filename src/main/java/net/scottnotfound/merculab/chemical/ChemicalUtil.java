package net.scottnotfound.merculab.chemical;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.scottnotfound.merculab.chemical.capability.CapabilityChemicalHandler;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandler;
import net.scottnotfound.merculab.chemical.capability.IChemicalHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChemicalUtil {
    private ChemicalUtil() {}

    /**
     * Used to handle the common case of a player holding a chemical item and right-clicking on a chemical handler block.
     * First it tries to fill the item from the block,
     * if that action fails then it tries to drain the item into the block.
     * Automatically updates the item in the player's hand and stashes any extra items created.
     *
     * @param player The player doing the interaction between the item and chemical handler block.
     * @param hand   The player's hand that is holding an item that should interact with the chemical handler block.
     * @param world  The world that contains the chemical handler block.
     * @param pos    The position of the chemical handler block in the world.
     * @param side   The side of the block to interact with. May be null.
     * @return true if the interaction succeeded and updated the item held by the player, false otherwise.
     */
    public static boolean interactWithChemicalHandler(@Nonnull EntityPlayer player,
                                                      @Nonnull EnumHand hand,
                                                      @Nonnull World world,
                                                      @Nonnull BlockPos pos,
                                                      @Nullable EnumFacing side) {
        Preconditions.checkNotNull(world);
        Preconditions.checkNotNull(pos);

        IChemicalHandler blockChemicalHandler = getChemicalHandler(world, pos, side);
        return blockChemicalHandler != null && interactWithChemicalHandler(player, hand, blockChemicalHandler);
    }

    /**
     * Used to handle the common case of a player holding a chemical item and right-clicking on a chemical handler.
     * First it tries to fill the item from the handler,
     * if that action fails then it tries to drain the item into the handler.
     * Automatically updates the item in the player's hand and stashes any extra items created.
     *
     * @param player  The player doing the interaction between the item and chemical handler.
     * @param hand    The player's hand that is holding an item that should interact with the chemical handler.
     * @param handler The chemical handler.
     * @return true if the interaction succeeded and updated the item held by the player, false otherwise.
     */
    public static boolean interactWithChemicalHandler(@Nonnull EntityPlayer player,
                                                      @Nonnull EnumHand hand,
                                                      @Nonnull IChemicalHandler handler) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(hand);
        Preconditions.checkNotNull(handler);

        ItemStack heldItem = player.getHeldItem(hand);
        if (!heldItem.isEmpty()) {
            IItemHandler playerInventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (playerInventory != null) {
                ChemicalActionResult chemicalActionResult = tryInsertContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);
                if (!chemicalActionResult.isSuccess()) {
                    chemicalActionResult = tryEmptyContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);
                }

                if (chemicalActionResult.isSuccess()) {
                    player.setHeldItem(hand, chemicalActionResult.getResult());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Fill a container from the given chemicalSource.
     *
     * @param container   The container to be filled. Will not be modified.
     *                    Separate handling must be done to reduce the stack size, stow containers, etc, on success.
     *                    See {@link  #tryInsertContainerAndStow(ItemStack, IChemicalHandler, IItemHandler, int, EntityPlayer, boolean)}.
     * @param chemicalSource The chemical handler to be extracted from.
     * @param maxAmount   The largest amount of chemical that should be transferred.
     * @param player      The player to make the inserting noise. Pass null for no noise.
     * @param doInsert      true if the container should actually be filled, false if it should be simulated.
     * @return a {@link ChemicalActionResult} holding the inserted container if successful.
     */
    @Nonnull
    public static ChemicalActionResult tryInsertContainer(@Nonnull ItemStack container,
                                                          IChemicalHandler chemicalSource,
                                                          int maxAmount,
                                                          @Nullable EntityPlayer player,
                                                          boolean doInsert) {
        ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(container, 1);
        IChemicalHandlerItem containerChemicalHandler = getChemicalHandler(containerCopy);
        if (containerChemicalHandler != null) {
            ChemicalStack simulatedTransfer = tryChemicalTransfer(containerChemicalHandler, chemicalSource, maxAmount, false);
            if (simulatedTransfer != null) {
                if (doInsert) {
                    tryChemicalTransfer(containerChemicalHandler, chemicalSource, maxAmount, true);
                    if (player != null) {
                        //todo: play a sound
                    }
                } else {
                    containerChemicalHandler.insert(simulatedTransfer, true);
                }

                ItemStack resultContainer = containerChemicalHandler.getContainer();
                return new ChemicalActionResult(resultContainer);
            }
        }
        return ChemicalActionResult.FAILURE;
    }

    /**
     * Takes a filled container and tries to empty it into the given container.
     *
     * @param container        The filled container. Will not be modified.
     *                         Separate handling must be done to reduce the stack size, stow containers, etc, on success.
     *                         See {@link #tryEmptyContainerAndStow(ItemStack, IChemicalHandler, IItemHandler, int, EntityPlayer, boolean)}.
     * @param chemicalDestination The chemical handler to be filled by the container.
     * @param maxAmount        The largest amount of chemical that should be transferred.
     * @param player           Player for making the bucket drained sound. Pass null for no noise.
     * @param doExtract          true if the container should actually be extracted from, false if it should be simulated.
     * @return a {@link ChemicalActionResult} holding the empty container if the chemical handler was inserted.
     *         NOTE If the container is consumable, the empty container will be null on success.
     */
    @Nonnull
    public static ChemicalActionResult tryEmptyContainer(@Nonnull ItemStack container,
                                                         IChemicalHandler chemicalDestination,
                                                         int maxAmount,
                                                         @Nullable EntityPlayer player,
                                                         boolean doExtract) {
        ItemStack containerCopy = ItemHandlerHelper.copyStackWithSize(container, 1);
        IChemicalHandlerItem containerChemicalHandler = getChemicalHandler(containerCopy);
        if (containerChemicalHandler != null) {
            if (doExtract) {
                ChemicalStack transfer = tryChemicalTransfer(chemicalDestination, containerChemicalHandler, maxAmount, true);
                if (transfer != null) {
                    if (player != null) {
                        //todo: play a sound
                    }
                    ItemStack resultContainer = containerChemicalHandler.getContainer();
                    return new ChemicalActionResult(resultContainer);
                }
            }
            else
            {
                ChemicalStack simulatedTransfer = tryChemicalTransfer(chemicalDestination, containerChemicalHandler, maxAmount, false);
                if (simulatedTransfer != null)
                {
                    containerChemicalHandler.extract(simulatedTransfer, true);
                    ItemStack resultContainer = containerChemicalHandler.getContainer();
                    return new ChemicalActionResult(resultContainer);
                }
            }
        }
        return ChemicalActionResult.FAILURE;
    }

    /**
     * Takes an Chemical Container Item and tries to fill it from the given container.
     * If the player is in creative mode, the container will not be modified on success, and no additional items created.
     * If the input itemstack has a stacksize > 1 it will stow the filled container in the given inventory.
     * If the inventory does not accept it, it will be given to the player or dropped at the players feet.
     *      If player is null in this case, the action will be aborted.
     *
     * @param container   The Chemical Container ItemStack to fill.
     *                    Will not be modified directly, if modifications are necessary a modified copy is returned in the result.
     * @param chemicalSource The chemical source to insert from
     * @param inventory   An inventory where any additionally created item (filled container if multiple empty are present) are put
     * @param maxAmount   Maximum amount of chemical to take from the container.
     * @param player      The player that gets the items the inventory can't take.
     *                    Can be null, only used if the inventory cannot take the filled stack.
     * @return a {@link ChemicalActionResult} holding the result and the resulting container. The resulting container is empty on failure.
     */
    @Nonnull
    public static ChemicalActionResult tryInsertContainerAndStow(@Nonnull ItemStack container,
                                                                 IChemicalHandler chemicalSource,
                                                                 IItemHandler inventory,
                                                                 int maxAmount,
                                                                 @Nullable EntityPlayer player,
                                                                 boolean doInsert) {
        if (container.isEmpty()) {
            return ChemicalActionResult.FAILURE;
        }

        if (player != null && player.capabilities.isCreativeMode) {
            ChemicalActionResult filledReal = tryInsertContainer(container, chemicalSource, maxAmount, player, doInsert);
            if (filledReal.isSuccess()) {
                return new ChemicalActionResult(container);
            }
        } else if (container.getCount() == 1) {
            ChemicalActionResult filledReal = tryInsertContainer(container, chemicalSource, maxAmount, player, doInsert);
            if (filledReal.isSuccess()) {
                return filledReal;
            }
        } else {
            ChemicalActionResult filledSimulated = tryInsertContainer(container, chemicalSource, maxAmount, player, false);
            if(filledSimulated.isSuccess()) {
                ItemStack remainder = ItemHandlerHelper.insertItemStacked(inventory, filledSimulated.getResult(), true);
                if (remainder.isEmpty() || player != null) {
                    ChemicalActionResult filledReal = tryInsertContainer(container, chemicalSource, maxAmount, player, doInsert);
                    remainder = ItemHandlerHelper.insertItemStacked(inventory, filledReal.getResult(), !doInsert);

                    if (!remainder.isEmpty() && player != null && doInsert) {
                        ItemHandlerHelper.giveItemToPlayer(player, remainder);
                    }

                    ItemStack containerCopy = container.copy();
                    containerCopy.shrink(1);
                    return new ChemicalActionResult(containerCopy);
                }
            }
        }
        return ChemicalActionResult.FAILURE;
    }

    /**
     * Takes an Chemical Container Item, tries to empty it into the chemical handler, and stows it in the given inventory.
     * If the player is in creative mode, the container will not be modified on success, and no additional items created.
     * If the input itemstack has a stacksize > 1 it will stow the emptied container in the given inventory.
     * If the inventory does not accept the emptied container, it will be given to the player or dropped at the players feet.
     *      If player is null in this case, the action will be aborted.
     *
     * @param container        The filled Chemical Container Itemstack to empty.
     *                         Will not be modified directly, if modifications are necessary a modified copy is returned in the result.
     * @param chemicalDestination The chemical destination to fill from the chemical container.
     * @param inventory        An inventory where any additionally created item (filled container if multiple empty are present) are put
     * @param maxAmount        Maximum amount of chemical to take from the tank.
     * @param player           The player that gets the items the inventory can't take. Can be null, only used if the inventory cannot take the filled stack.
     * @param doExtract          true if the container should actually be extracted, false if it should be simulated.
     * @return a {@link ChemicalActionResult} holding the result and the resulting container. The resulting container is empty on failure.
     */
    @Nonnull
    public static ChemicalActionResult tryEmptyContainerAndStow(@Nonnull ItemStack container,
                                                                IChemicalHandler chemicalDestination,
                                                                IItemHandler inventory,
                                                                int maxAmount,
                                                                @Nullable EntityPlayer player,
                                                                boolean doExtract) {
        if (container.isEmpty()) {
            return ChemicalActionResult.FAILURE;
        }

        if (player != null && player.capabilities.isCreativeMode) {
            ChemicalActionResult emptiedReal = tryEmptyContainer(container, chemicalDestination, maxAmount, player, doExtract);
            if (emptiedReal.isSuccess()) {
                return new ChemicalActionResult(container);
            }
        } else if (container.getCount() == 1) {
            ChemicalActionResult emptiedReal = tryEmptyContainer(container, chemicalDestination, maxAmount, player, doExtract);
            if (emptiedReal.isSuccess()) {
                return emptiedReal;
            }
        } else {
            ChemicalActionResult emptiedSimulated = tryEmptyContainer(container, chemicalDestination, maxAmount, player, false);
            if (emptiedSimulated.isSuccess()) {
                ItemStack remainder = ItemHandlerHelper.insertItemStacked(inventory, emptiedSimulated.getResult(), true);
                if (remainder.isEmpty() || player != null) {
                    ChemicalActionResult emptiedReal = tryEmptyContainer(container, chemicalDestination, maxAmount, player, doExtract);
                    remainder = ItemHandlerHelper.insertItemStacked(inventory, emptiedReal.getResult(), !doExtract);

                    if (!remainder.isEmpty() && player != null && doExtract) {
                        ItemHandlerHelper.giveItemToPlayer(player, remainder);
                    }

                    ItemStack containerCopy = container.copy();
                    containerCopy.shrink(1);
                    return new ChemicalActionResult(containerCopy);
                }
            }
        }
        return ChemicalActionResult.FAILURE;
    }

    /**
     * Fill a destination chemical handler from a source chemical handler with a max amount.
     * To specify a chemical to transfer instead of max amount, use {@link #tryChemicalTransfer(IChemicalHandler, IChemicalHandler, ChemicalStack, boolean)}
     * To transfer as much as possible, use {@link Integer#MAX_VALUE} for maxAmount.
     *
     * @param chemicalDestination The chemical handler to be inserted into.
     * @param chemicalSource      The chemical handler to be extracted from.
     * @param maxAmount        The largest amount of fluid that should be transferred.
     * @param doTransfer       True if the transfer should actually be done, false if it should be simulated.
     * @return the chemicalStack that was transferred from the source to the destination. null on failure.
     */
    @Nullable
    public static ChemicalStack tryChemicalTransfer(IChemicalHandler chemicalDestination, IChemicalHandler chemicalSource, int maxAmount, boolean doTransfer) {
        ChemicalStack extractable = chemicalSource.extract(maxAmount, false);
        if (extractable != null && extractable.amount > 0) {
            return tryChemicalTransfer_Internal(chemicalDestination, chemicalSource, extractable, doTransfer);
        }
        return null;
    }

    /**
     * Fill a destination chemical handler from a source chemical handler using a specific chemical.
     * To specify a max amount to transfer instead of specific chemical, use {@link #tryChemicalTransfer(IChemicalHandler, IChemicalHandler, int, boolean)}
     * To transfer as much as possible, use {@link Integer#MAX_VALUE} for resource.amount.
     *
     * @param chemicalDestination The chemical handler to be inserted into.
     * @param chemicalSource      The chemical handler to be extracted from.
     * @param resource         The chemical that should be transferred. Amount represents the maximum amount to transfer.
     * @param doTransfer       True if the transfer should actually be done, false if it should be simulated.
     * @return the chemicalStack that was transferred from the source to the destination. null on failure.
     */
    @Nullable
    public static ChemicalStack tryChemicalTransfer(IChemicalHandler chemicalDestination, IChemicalHandler chemicalSource, ChemicalStack resource, boolean doTransfer) {
        ChemicalStack extractable = chemicalSource.extract(resource, false);
        if (extractable != null && extractable.amount > 0 && resource.isChemicalEqual(extractable)) {
            return tryChemicalTransfer_Internal(chemicalDestination, chemicalSource, extractable, doTransfer);
        }
        return null;
    }

    /**
     * Internal method for filling a destination chemical handler from a source chemical handler using a specific chemical.
     * Assumes that "extractable" can be extracted from "chemicalSource".
     */
    @Nullable
    private static ChemicalStack tryChemicalTransfer_Internal(IChemicalHandler chemicalDestination, IChemicalHandler chemicalSource, ChemicalStack extractable, boolean doTransfer) {
        int fillableAmount = chemicalDestination.insert(extractable, false);
        if (fillableAmount > 0) {
            if (doTransfer) {
                ChemicalStack extracted = chemicalSource.extract(fillableAmount, true);
                if (extracted != null) {
                    extracted.amount = chemicalDestination.insert(extracted, true);
                }
            } else {
                extractable.amount = fillableAmount;
                return extractable;
            }
        }
        return null;
    }

    /**
     * Helper method to get an {@link IChemicalHandlerItem} for an itemStack.
     */
    @Nullable
    public static IChemicalHandlerItem getChemicalHandler(@Nonnull ItemStack itemStack) {
        return itemStack.hasCapability(CapabilityChemicalHandler.CHEMICAL_HANDLER_ITEM_CAPABILITY, null) ?
               itemStack.getCapability(CapabilityChemicalHandler.CHEMICAL_HANDLER_ITEM_CAPABILITY, null) :
               null;
    }

    /**
     * Helper method to get the chemical contained in an itemStack
     */
    @Nullable
    public static ChemicalStack getChemicalContained(@Nonnull ItemStack container) {
        if (container.isEmpty()) {
            container = ItemHandlerHelper.copyStackWithSize(container, 1);
            IChemicalHandlerItem chemicalHandler = getChemicalHandler(container);
            if (chemicalHandler != null) {
                return chemicalHandler.extract(Integer.MAX_VALUE, false);
            }
        }
        return null;
    }

    /**
     * Helper method to get an {@link IChemicalHandler} for a block position.
     */
    @Nullable
    public static IChemicalHandler getChemicalHandler(World world, BlockPos blockPos, @Nullable EnumFacing side) {
        IBlockState state = world.getBlockState(blockPos);
        Block block = state.getBlock();

        if (block.hasTileEntity(state)) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity != null && tileEntity.hasCapability(CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY, side)) {
                return tileEntity.getCapability(CapabilityChemicalHandler.CHEMICAL_HANDLER_CAPABILITY, side);
            }
        }
        return null;
    }

}
