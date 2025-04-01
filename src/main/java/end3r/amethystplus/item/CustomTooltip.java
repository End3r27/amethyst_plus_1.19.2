   package end3r.amethystplus.item;

   import net.fabricmc.api.ClientModInitializer;
   import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
   import net.minecraft.item.Items;
   import net.minecraft.text.Text;
   import net.minecraft.util.Formatting;

   public class CustomTooltip implements ClientModInitializer {

       @Override
       public void onInitializeClient() {
           // Register a tooltip callback for all items
           ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
               // Check if the item is amethyst_shard
               if (stack.isOf(Items.AMETHYST_SHARD)) {
                   // Add a tooltip (Text.literal allows direct string input)
                   lines.add(Text.literal("Try putting this in a charging station...").formatted(Formatting.GRAY));
               }
           });
       }
   }