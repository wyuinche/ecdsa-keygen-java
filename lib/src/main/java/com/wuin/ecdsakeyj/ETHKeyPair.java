package com.wuin.ecdsakeyj;

import org.bitcoinj.core.*;
import org.bitcoinj.params.*;

public class ETHKeyPair extends ECDSAKeyPair {
    private ECKey kepair;

    public ETHKeyPair() {
        String _priv = createPrivateKey();
        if(_priv != null) {
            this.priv = _priv;
        }

        createPublicKey();
    }    

    public ETHKeyPair(String priv) {
        super(priv);
        createPublicKey();
    }

    private String createPrivateKey() {
        try {
            return new ECKey().getPrivateKeyAsHex();
        } catch(Exception e) {
            Util.raiseError("Fail to generate new ETHER Keypair.");
            return null;
        }
    }

    @Override
    public void createPublicKey() {
        this.keypair = ECKey.fromPrivate(hexStringToBytes(this.priv));
        
    }

    @Override
    public byte[] sign(String msg) {
        return sign(msg.getBytes());
    }

    @Override
    public byte[] sign(byte[] target){
        return new byte[0];
    }
}
