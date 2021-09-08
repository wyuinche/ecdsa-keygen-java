package com.wuin.ecdsakeyj;

public abstract class ECDSAKeyPair {
    protected String priv;
    protected String pub;

    protected ECDSAKeyPair() {
        this.priv = "";
        this.pub = "";
    }

    protected ECDSAKeyPair(String priv) {
        this.priv = priv;
    }

    public String getPrivateKey() {
        return this.priv;
    }

    public String getPublicKey() {
        return this.pub;
    }

    public abstract void createPublicKey();
    public abstract byte[] sign(String msg);
    public abstract byte[] sign(byte[] target);
}
     