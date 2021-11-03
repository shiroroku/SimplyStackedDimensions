Adds data options for teleporting players to and from different dimensions when at specific Y levels!

### Dimension Config:
There are no config files by default and running the mod without adding one wont change the game.

The example config for teleporting the player to the nether when at Y level -10 to 1:
```json
{
    "from": "minecraft:overworld",
    "to": "minecraft:the_nether",
    "from_y_min": -10,
    "from_y_max": 1,
    "to_y": 121,
    "cloud_platform": true
}
```

This file can be named whatever as long as its a JSON in the mods config data folder (/data/simplystacked/config/)

| Key | Value | Description |
| --- | --- | --- |
| from | String | Dimension ID the player has to be in to trigger the teleport |
| to | String | Dimension ID the player will be teleported to |
| from_y_min | Integer | Minimum Y the player must be at to trigger the teleport |
| from_y_max | Integer | Maximum Y the player must be at to trigger the teleport |
| to_y | Integer | Y level the player will be teleported at |
| cloud_platform | Boolean | If a cloud platform should be generated beneath the teleport destination |

### Additional Info:
- Clouds are blocks that can be spawned below the teleport destination to keep the player from falling back down. They will disappear over time.
- Teleporting does not modify X and Z values, so nether distance per overworld block is not preserved.
- Teleport cooldown can be changed in Common Config, by default its three seconds (60 ticks).
