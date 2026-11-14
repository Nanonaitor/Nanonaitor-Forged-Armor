# Equipped Attachment Model Reference

Every equipped attachment is built from the source mob's **vanilla 1.20.1 model parts**: the same boxes, UV
offsets, texture size, mirroring and default poses Minecraft uses, so the pieces look the way they do on the mob.
Each rig then gets one transform that sizes the piece for a player and places it just outside the armor shell.

Everything lives in one spec, `tools/attachment_rigs.py`. Running it regenerates:

- `src/main/java/com/nanonaitor/forgedarmor/client/AttachmentRigs.java` (do not edit by hand)
- generated textures in `textures/entity/attachment/` (heart/core voxel atlases and the breeze wind rings)
- optionally, a three.js preview page of every rig on an iron-armored Steve

```bash
python tools/attachment_rigs.py
```

To also build the preview, pass a folder of extracted vanilla `assets/minecraft/textures` and an output path:

```bash
python tools/attachment_rigs.py path/to/assets/minecraft/textures build/attachment_preview.html
```

The preview must be served over HTTP (for example `python -m http.server`) and supports `?filter=head|body|legs`,
`?only=id1,id2` and `?big`.

## Tuning a rig

`vertex_on_player = dst + scale * rotation(rot_deg) * (vertex_in_vanilla_space - src)`, in model pixels with y down
and -z pointing out of the player's face or chest. The armor shells sit at roughly ±5 around the head (helmet),
z = -3 / +3 on the torso (chestplate), ±2.5 on the legs (leggings) and ±3 with the sole at y = 13 (boots).
Leg rigs can use `left` / `right` part lists for pieces that differ per leg. Saved armor NBT only stores attachment
IDs, so models can be reshaped freely.

## Helmet

| Attachment | Anchor | Vanilla source |
|---|---|---|
| Ravager Horn | Head | Ravager horns |
| Goat Horn | Head | Goat horns |
| Pillager Nose | Head | Illager nose |
| Zombie Head | Head | Zombie head skull (with hat layer), sized to the player head |
| Skeleton Skull | Head | Skeleton skull |
| Creeper Head | Head | Creeper head |
| Spider Mask | Head | Spider head with glowing eye layer |
| Hoglin Horn | Head | Hoglin tusks, at the chin |
| Bunny Ears | Head | Rabbit ears |
| Dragon Head | Head | Dragon head skull and jaw |
| Warden Ears | Head | Warden tendrils |

Zombie, Skeleton, Creeper, Spider and Dragon heads replace the wearer's head: the player's head, hat layer and
helmet are hidden while one is attached (`MobHeadVisibility` and `HumanoidArmorLayerMixin`).

## Chestplate

| Attachment | Anchor | Vanilla source |
|---|---|---|
| Witch, Iron Golem, Creaking and Warden Hearts | Chest | Item sprite extruded into voxels like a held item |
| Snow Golem Ice Core, Iron Golem Iron Core | Chest | Item sprite extruded into voxels like a held item |
| Turtle Shell | Back | Sea turtle shell |
| Allay Wings | Back | Allay wings in their flying pose (translucent) |
| Vex Wings | Back | Vex wings (translucent) |
| Shulker Shell | Back | Closed shulker lid and base as a backpack |
| Phantom Wings | Back | Phantom wing bases and tips, spread vertically |
| Elytra | Back | Vanilla elytra layer (unchanged) |

## Leggings

| Attachment | Anchor | Vanilla source |
|---|---|---|
| Bee Stinger | Waist | Bee stinger |
| Parrot Tail | Waist | Red macaw parrot tail |
| Camel Tail | Waist | Camel tail |
| Guardian Spikes | Each knee | Three guardian spikes |
| Shulker Plates | Each knee | Shulker lid |
| Ravager Hide | Each knee | Ravager leg hide |
| Silverfish Carapace | Each knee | Silverfish body plates |
| Iron Golem Plate | Each knee | Iron golem leg plating |

## Boots

| Attachment | Anchor | Vanilla source |
|---|---|---|
| Elder Guardian Spikes | Each ankle | Elder guardian spikes |
| Strider Feet | Each foot | Strider leg ends |
| Rabbit Feet | Each foot | Rabbit hind feet |
| Slime | Each foot | Slime outer and inner cube with face (translucent) |
| Breeze Wind | Each ankle | Three translucent wind rings (Breeze is not in 1.20.1) |
| Frog Legs | Each foot | Frog thighs and webbed feet |
| Piglin Hooves | Each foot | Piglin leg hooves |
| Hoglin Hooves | Each foot | Hoglin hooves |
| Spider Legs | Each ankle | Spider legs, two per side |
