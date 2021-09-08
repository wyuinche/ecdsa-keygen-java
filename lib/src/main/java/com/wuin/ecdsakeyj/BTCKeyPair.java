package com.wuin.ecdsakeyj;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bitcoinj.core.*;
import org.bitcoinj.params.*;
import org.bouncycastle.jce.provider.*;

public class BTCKeyPair extends ECDSAKeyPair {
    private ECKey keypair;

    public BTCKeyPair() {
        String _priv = createPrivateKey();
        if(_priv != null) {
            this.priv = _priv;
        }

        createPublicKey();
    }

    public BTCKeyPair(String priv) {
        super(priv);
        createPublicKey();
    }
    
    private String createPrivateKey() {
        Security.addProvider(new BouncyCastleProvider());
        
        try {    
            KeyPairGenerator gen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec spec = new ECGenParameterSpec("secp256k1");
            gen.initialize(spec);

            KeyPair btcKeyPair = gen.generateKeyPair();

            String _temp = btcKeyPair.getPrivate().toString();
            int idx = _temp.indexOf(":") + 2;
            String _pk = "80" + _temp.substring(idx, idx+64) + "01";

            byte[] bytePk = Util.hexStringToBytes(_pk);

            byte[] _hash = new Hash(new Hash(bytePk).getSha256Digest()).getSha256Digest();
            byte[] checksum = new byte[4];
            System.arraycopy(_hash, 0, checksum, 0, 4);

            byte[] pk = new byte[bytePk.length + 4];
            System.arraycopy(bytePk, 0, pk, 0, bytePk.length);
            System.arraycopy(checksum, 0, pk, bytePk.length, 4);

            String encoded = Base58.encode(pk);
            return encoded;
        } catch(Exception e) {
            Util.raiseError("Fail to create new private key.");
            return null;
        }
    }

    @Override
    public void createPublicKey() {
        NetworkParameters params = MainNetParams.get();

        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, this.priv);
        this.keypair = dumpedPrivateKey.getKey();

        String _pub = this.keypair.getPublicKeyAsHex();
        byte[] _bpub = Util.hexStringToBytes(_pub);

        this.pub = Base58.encode(_bpub);
    }

    @Override
    public byte[] sign(String msg) {
        return sign(msg.getBytes());
    }

    @Override
    public byte[] sign(byte[] target) {
        return sign(new String(target));
    }
}
