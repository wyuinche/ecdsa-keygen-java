package com.wuin.ecdsakeyj;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bitcoinj.core.*;
import org.bitcoinj.params.*;
import org.bouncycastle.jce.provider.*;

public class ETHKeyPair extends ECDSAKeyPair {
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
        Security.addProvider(new BouncyCastleProvider());

        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec spec = new ECGenParameterSpec("secp256k1");
            gen.initialize(spec);

            KeyPair etherKeypair = gen.generateKeyPair();
            String _temp = etherKeypair.getPrivate().toString();
            
            int idx = _temp.indexOf(":") + 2;
            return _temp.substring(idx, idx + 64);
        } catch(Exception e) {
            Util.raiseError("Fail to create new private key.");
            return null;
        }
    }

    @Override
    public void createPublicKey() {
        this.pub = "";

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
