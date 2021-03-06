# ecdsa-keygen-java
ECDSA KeyPair Generator for Java

This library supports ecdsa keypair(btc compressed wif and ether).

This library is for [mitum-java-util](https://github.com/ProtoconNet/mitum-java-util).

## Installation

This project has developed by,

* Gradle v7.3.3
* OpenJDK v17.0.1
* javac v17.0.1

```sh
$ gradle --version

------------------------------------------------------------
Gradle 7.3.3
------------------------------------------------------------

Build time:   2021-12-22 12:37:54 UTC
Revision:     6f556c80f945dc54b50e0be633da6c62dbe8dc71

Kotlin:       1.5.31
Groovy:       3.0.9
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          17.0.1 (Oracle Corporation 17.0.1+12-LTS-39)
OS:           Mac OS X 12.0.1 aarch64

$ java --version
java 17.0.1 2021-10-19 LTS
Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)

$ javac --version
javac 17.0.1
```

And this project includes below external libraries.

* Simple Logging Facade for Java (SLF4J) v1.7.32
* bitcoinj core v0.15.10
* web3j core v4.8.7

Download and use 'ecdsa-keygen-java-1.4-jdk17.jar' in 'release' tab or [here](release/).

```sh
implementation files('./ecdsa-keygen-java-1.4-jdk17.jar')
```

## BTCKeyPair

'BTCKeyPair' support to sign message with btc keypairs.

```java
String BTCKeyPair.getPrivateKey() // returns compressed btc private key
String BTCKeyPair.getPublicKey() //returns base58 encoded btc public key
byte[] BTCKeyPair.sign(byte[] msg) // returns signature signed with private key
```

## ETHKeyPair

'ETHKeyPair' support to sign message with ether keypairs.

```java
String ETHKeyPair.getPrivateKey() // returns ether private key
String ETHKeyPair.getPublicKey() //returns ether public key
byte[] ETHKeyPair.sign(byte[] msg) // returns signature signed with private key
```

## Usage

This section will introduce to create or import keypair and sign message.

Import path for every class is 'com.wuin.ecdsakeyj'

```java
import com.wuin.ecdsakeyj;
```

### Create New KeyPair

```java
BTCKeyPair btcKeyPair = BTCKeyPair.create();
ETHKeyPair ethKeyPair = ETHKeyPair.create();

System.out.println(btcKeyPair.getPrivateKey());
System.out.println(btcKeyPair.getPublicKey());

System.out.println(ethKeyPair.getPrivateKey());
System.out.println(ethKeyPair.getPublicKey());
```

-must print,

```sh
KxCbvcivc8tzBuKhVd8ns9NWCCVkf2p5NbDEeQcdi1i8SuhsVcs2
23Vey3qxRiCLKBQCfiu6tx3KyhSdA6KJ1hqmyZpGCVPac
b86a76d7597b9970d224dfc4008d94f8bc94718e2dbce4523a8f20ff7bc95c9d
04b4157e18cc84c14110d897a04c34195d4830523d028b77eb6bdeafb0c1d119930c6d884530234e9f21e8ebf01f1fb5d1f5ec958f4795b024819a51fbf5700c
```

Note that the result is up to each keypair.

### Create KeyPair from Private Key

```java
String btcPriv = "Ky7uryfGoK4wnNPXEP71yeM64HN4KKhhvV4Kn7VZyHovcpatNkQX";
String ethPriv = "4ca85fd2d908a77e5b6e5d15e222ae70d1d70c5101cd880a7e2ff2796c6d56ac";

BTCKeyPair btcKeyPair = BTCKeyPair.fromPrivateKey(btcPriv);
ETHKeyPair ethKeyPair = ETHKeyPair.fromPrivateKey(ethPriv);

System.out.println(btcKeyPair.getPrivateKey());
System.out.println(btcKeyPair.getPublicKey());

System.out.println(ethKeyPair.getPrivateKey());
System.out.println(ethKeyPair.getPublicKey());
```

-must print,

```sh
Ky7uryfGoK4wnNPXEP71yeM64HN4KKhhvV4Kn7VZyHovcpatNkQX
wRcd8Nkcss8ChuCAQnK6CEevaPTXnCdikVvTH3eNd7DJ
4ca85fd2d908a77e5b6e5d15e222ae70d1d70c5101cd880a7e2ff2796c6d56ac
04fc2887ca1d0a5b2a9ca0fd2ea82a29112ad61fde75d83de1b9c291b4bdd38363fa8a6950161dabec102c780d4d9d76c7a24d52f7979f5524d47d906727ed2c41
```

### Create BTCKeyPair from Seed

```java
String seed = "This is a seed to create new BTCKePair";
byte[] bseed = seed.getBytes(StandardCharsets.UTF_8);

BTCKeyPair btcKeyPair = BTCKeyPair.fromSeed(seed);
BTCKeyPair sbtcKeyPair = BTCKeyPair.fromSeed(bseed);

System.out.println(btcKeyPair.getPrivateKey());
System.out.println(btcKeyPair.getPublicKey());

System.out.println(sbtcKeyPair.getPrivateKey());
System.out.println(sbtcKeyPair.getPublicKey());
```

-must print,

```sh
KzWdyW7X8pKprFSH3rUfGSor19oiBBVarjBakV2GjJjbn9CNjsxF
mxfoQxXkbvfenQmeFXqzY5gvMY3KnTaPvGHkyhhQnRZb
```

### Sign Message with KeyPair

```java
BTCKeyPair btcKeyPair = BTCKeyPair.create();
ETHKeyPair ethKeyPair = ETHKeyPair.create();

String msg = "Hello, world!";

byte[] btcSig = btcKeyPair.sign(msg.getBytes());
byte[] etherSig = ethKeyPair.sign(msg.getBytes());
```

Note that the result is up to each keypair.