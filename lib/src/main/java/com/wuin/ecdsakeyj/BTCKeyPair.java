package com.wuin.ecdsakeyj;

import org.bitcoinj.core.*;
import org.bitcoinj.params.*;

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
        try {    
            ECKey keypair = new ECKey();
            String _priv = "80" + keypair.getPrivateKeyAsHex() + "01";
            byte[] _bpriv = hexStringToBytes(_priv);

            byte[] _hs = Sha256Hash.hashTwice(_bpriv);

            byte[] checksum = new byte[4];
            System.arraycopy(_hs, 0, checksum, 0, 4);

            byte[] _pk = new byte[_bpriv.length + 4];
            System.arraycopy(_bpriv, 0, _pk, 0, _bpriv.length);
            System.arraycopy(checksum, 0, _pk, _bpriv.length, 4);

            return Base58.encode(_pk);
        } catch(Exception e) {
            Util.raiseError("Fail to generate new BTC Keypair.");
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
        return keypair.sign(Sha256Hash.twiceOf(target)).encodeToDER();
    }
}
