# java-chip8
A simple CHIP-8 implementation written in Java.

## VM description
- **Opcodes**: 35 opcodes: all 16-bit long.

- **RAM memory**: 4096 memory locations, all of which are 8-bit long.

- **V0-VF**: 15 general purpose CPU registers, all of which are 8-bit long. VF is used for "carry flag".

- **I**: 1 Index Register, 16-bit long. Points at locations in memory.

- **PC**: 1 Program Counter, 16-bit long. Points at current instruction in memory.

- **Display**: black and white graphics, 2048 pixels in total (resolution: 64 x 32).

- **Stack**: stores 16-bit addresses and has 16 levels. Used to remember the current location before jump is performed.

- **SP**: 1 Stack Pointer, 8-bit long. Used to remember which level of the stack is currently used.

- **Delay Timer**: 8-bit timer register that counts at 60Hz, is decremented at a rate of 60Hz until it reaches 0.

- **Sound Timer**: 8-bit timer register that counts at 60Hz, is decremented at a rate of 60Hz until it reaches 0. It gives off a beeping sound when its value is non-zero.

## Used ROMs
- [IBM logo](https://github.com/loktar00/chip8/blob/master/roms/IBM%20Logo.ch8)

- [Octojam 9 Title](https://johnearnest.github.io/chip8Archive/play.html?p=octojam9title)

## Help message
```
Usage: <jar file name> [-hV] -r=<romPath> [-s=<randomSeed>] [-lo | -li | -ld |
                       -lt]
A simple CHIP-8 implementation written in Java.
  -h, --help                Show this help message and exit.
      -ld, --log-debug      Enable logging level DEBUG.
      -li, --log-info       Enable logging level INFO [default].
      -lo, --log-off        Disable logging.
      -lt, --log-trace      Enable logging level TRACE.
  -r, --rom=<romPath>       Path to CHIP-8 ROM file.
  -s, --seed=<randomSeed>   Set random seed.
  -V, --version             Print version information and exit.
```

## Build JAR using make command
```bash
user@host:~$ make package
```

## Build JAR using just command
```bash
user@host:~$ just package
```

## Build JAR using Maven
```bash
user@host:~$ mvn package
```

## Run JAR file
```bash
user@host:~$ java -jar JavaChip8-1.0.1.jar -r roms/octojam9title.ch8
```

## References
Some really helpful references that I used:

- [CHIP-8 Technical Reference v1.0](http://devernay.free.fr/hacks/chip8/C8TECH10.HTM)

- [How to write an emulator](https://multigesture.net/articles/how-to-write-an-emulator-chip-8-interpreter/)

- [Guide to making a CHIP-8 emulator](https://tobiasvl.github.io/blog/write-a-chip-8-emulator)

- [CHIP-8](https://www.wikiwand.com/en/CHIP-8)

- [CHIP-8 Emulator](https://github.com/brokenprogrammer/CHIP-8-Emulator)

A really useful repository for CHIP-8 ROMs:

- [CHIP-8 Archive](https://johnearnest.github.io/chip8Archive)
