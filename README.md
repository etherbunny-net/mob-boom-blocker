# Mob Boom Blocker 💥

Simple Craftbukkit plugin that prevents mob explosions from damaging blocks,
while still hurting players in survival. Only explosions from TNT damage blocks.

## Installation

Copy the `.jar` file from the [releases] page into your server's `plugins/`
directory.

## How it Works

This plugin installs an `EntityExplodeEvent` listener. If the entity isn't
allowed to damage blocks, the event's `blockList` is cleared. The event isn't
canceled or otherwise altered, so entities like players will still be hurt.

The policy is currently hard-coded: only a `PrimedTNT` entity is allowed to
damage blocks.

## Reporting Bugs

Report bugs on the [issues] page.

[releases]: https://github.com/etherbunny-net/mob-boom-blocker/releases
[issues]: https://github.com/etherbunny-net/mob-boom-blocker/issues
