# 0.2.0

- Gave the Armor Forge its own screen texture with an attachment-position row that shows what fills each position, which one an attachment will fill or replace, and a red cross when the inputs do not fit.
- The Armor Forge now removes attachments: place armor on its own to take it out bare and get every attachment back. Enchantments are kept.
- Forged armor shows a small green gem per attachment in every inventory slot, and its tooltip lists each attachment with its effect.
- Added the Forged Armor Guide (book + copper ingot), listing every attachment, where it fits, what it does and which mobs drop it at what chance.
- Added optional JEI support with an info page for the Armor Forge and every attachment.
- New config options: per-attachment drop chances, a disabled-attachments list, and strengths for most abilities (speed, armor, poison, thorns, shield reduction, jump, swim, double jump, dash strength and cooldown).
- Wings flap, tails sway, Breeze Wind rings spin and Warden tendrils twitch while worn.
- Abilities now show particles and play sounds: double jump, dash, glide, Creaking Heart dodge, Slime bounce, Bee Stinger, Witch Heart cleanse, Iron Golem Heart repair and Warden Heart distraction.
- Added Camel Tail art and redrew Bunny Ears.
- Added game tests for forging, swapping, removal, legacy Turtle Shell data and item assets (`gradlew runGameTestServer`).
- Removed the outdated Blockbench files and their generator.

# 0.1.9

- Moved Turtle Shell from the Chest attachment position to the Back position on chestplates.
- Preserved legacy Turtle Shell chestplate data by recognizing and migrating its old Chest-slot entry.
- Added a diamond armor-piece icon to every attachment tooltip.
- Labeled every upgrade with both its compatible armor piece and its attachment position.
- Redrew the attachment item sprites, and added the missing Spider Legs sprite.
- Removed the red placeholder bar from the Armor Forge screen and centred the "Armor Forge" title there.
- Mob-head attachments (Zombie, Skeleton, Creeper, Spider and Dragon) are now sized to the player's head and hide the wearer's head and helmet.
- Moved Frog Legs forward on the boot and lowered Piglin, Strider and Hoglin hooves one pixel to stop flickering against the boot sole.

# 0.1.4

- Removed Donkey Tail and cleanly ignored its old missing item mapping.
- Disabled Camel Tail pending Tough As Nails thirst and temperature compatibility.
- Replaced duplicate attachments with their proper vanilla Goat Horn, mob head, Turtle Shell, Shulker Shell, Rabbit's Foot, Slimeball and Elytra items.
- Added automatic remapping from the removed duplicate item IDs to their vanilla equivalents for existing worlds.
- Corrected Hoglin Hooves so melee attacks made by the wearer knock the target away.
- Corrected Strider Feet so players can stand and walk directly on lava using Minecraft's fluid-standing behavior.
- Restricted Warden Ears highlighting to hostile mobs and mobs actively targeting the wearer.

# 0.1.3

- Added the vanilla Elytra as a chestplate Back-slot attachment.
- Added normal Elytra takeoff, flight physics, sounds and flight animation while keeping the chestplate equipped.
- Rendered Minecraft's animated Elytra wings as part of the forged chestplate.
- Redirected Elytra flight wear to the chestplate at the vanilla rate of one durability per second.
- Made flight durability respect Unbreaking and stop at one remaining durability instead of breaking the chestplate.
- Added replacement support that returns an Elytra when another Back attachment replaces it.

# 0.1.2

- Added a dedicated Nanonaitor Forged Armor creative-mode tab.
- Used the green Armor Forge as the tab icon.
- Added the Armor Forge and every attachment to the tab in attachment-list order.

# 0.1.1

- Added equipped 3D visuals for every armor attachment.
- Anchored horns, masks, chest pieces, wings, tails, knee pieces and footwear to the matching animated player body part.
- Added distinct silhouettes for horns, ears, masks, hearts, cores, shells, wings, tails, plates, spikes, feet, hooves and spider legs.
- Reused source-mob textures so each attachment visually refers back to the creature that supplies it.
- Kept every attachment as an independent model child for easy per-item resizing and placement adjustments.

# 0.1.0

- Added the green Armor Forge workstation.
- Added persistent Horns, Face, Chest, Back, Knees, Tail, Soles and Top armor slots.
- Added the first 40 mob and treasure attachments.
- Added configurable mob drop, Looting and Luck chances.
- Added End City treasure support for the Ender Dragon Mask.
- Added combat, movement, defense, neutrality, repair, cleansing, storage, highlighting and mobility abilities.
- Added replacement support that returns an attachment already installed in the same slot.
- Added initial compatibility hooks for temperature effects, Pale Bloom Creakings and backported Breeze entities.
# 0.1.8

- Scaled attachment sprites down within inventory slots while preserving their true 16x16 source grid.
- Kept every inventory icon on a flat generated-item model so equipped 3D rigs cannot obscure sprite rendering.
- Fixed Allay and Vex Wings so pressing Jump again after leaving the ground performs one synchronized midair jump.
- Added forged attachment and Elytra rendering to armor stands, following their configured poses.

# 0.1.7

- Replaced attachment inventory art with a consistent true 16x16 vanilla-style sprite set.
- Added matching sprites for every custom drop from Ravager Horn through Hoglin Hooves.
- Restyled Spider Mask as a dimensional mob-head item with the spider's dark face, red eyes, and fangs.
- Kept Elytra and Shulker Shell on their exact vanilla item art rather than duplicating those existing items.

# 0.1.6

- Restyled the Armor Forge screen using Minecraft's standard anvil interface.
- Added the normal anvil-use sound when an attachment is successfully forged onto armor.
- Redrew Ravager Horn, Bunny Ears, Allay Wings, and Turtle Shell as detailed 32x32 Minecraft pixel-art items matching their concept silhouettes.

# 0.1.5

- Added polished player-scale models for Ravager Horns, Bunny Ears, Allay Wings, and the Turtle Shell.
- Added editable Blockbench source files with consistent head and back anchors.
- Replaced placeholder inventory art for those four attachments with dedicated 16x16 pixel art.
- Made Turtle Shell a dedicated Forged Armor attachment item instead of reusing the Turtle Helmet icon.
