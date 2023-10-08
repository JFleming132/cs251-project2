public class BlockChainSecure {
    public FancyBlockChain fbc;
    public Block[] btable;

    public BlockChainSecure(int capacity) {
        fbc = new FancyBlockChain(capacity);
        int n;
        for (n = capacity + 1; !isPrime(n); n++) {
            continue;
        }
        btable = new Block[n];
    }
    public BlockChainSecure(Block[] initialBlocks) {
        fbc = new FancyBlockChain(initialBlocks);
        int n;
        for (n = initialBlocks.length + 1; !isPrime(n); n++) {
            continue;
        }
        btable = new Block[n];
        for (Block initialBlock : initialBlocks) {
            addBlock(initialBlock);
        }
    }

    public int length() {
        return fbc.length();
    }

    public boolean addBlock(Block newBlock) {
        return false;
    }
    public Block getEarliestBlock() {
        return fbc.getEarliestBlock();
    }
    public Block getBlock(String data) {
        int hashedIndex = Hasher.hash1(data, btable.length) % btable.length;
        for (int i = 1; (i <= 1) || (hashedIndex != Hasher.hash1(data, btable.length)); i++) {
            if (btable[hashedIndex].data.equals(data) || btable[hashedIndex] == null) {
                return btable[hashedIndex];
            }
            hashedIndex = (Hasher.hash1(data, btable.length) + i * Hasher.hash2(data, btable.length)) % btable.length;
        }
        for (int i = 0; (i == 0) || (hashedIndex != Hasher.hash1(data, btable.length)); i++) {
            if (btable[hashedIndex].data.equals(data) || btable[hashedIndex] == null) {
                return btable[hashedIndex];
            }
            hashedIndex = (Hasher.hash1(data, btable.length) + i) % btable.length;

        }
        return null;
    }
    public Block removeEarliestBlock() {
        return fbc.removeEarliestBlock();
    }
    public Block removeBlock(String data) {
        Block returnBlock = getBlock(data);
        fbc.removeBlock(returnBlock.index);
        return returnBlock;
    }
    public void updateEarliestBlock(double nonce) {
        Block newBlock = removeEarliestBlock();
        if (newBlock != null) {
            fbc.latestTimestamp++;
            newBlock.timestamp = fbc.latestTimestamp;
            newBlock.nonce = nonce;
            addBlock(newBlock);
        }
    }
    public void updateBlock(String data, double nonce) {
        Block newBlock = removeBlock(data);
        if (newBlock != null) {
            fbc.latestTimestamp++;
            newBlock.timestamp = fbc.latestTimestamp;
            newBlock.nonce = nonce;
            addBlock(newBlock);
        }
    }

    public boolean isPrime(int i) {
        if (i <= 1) {
            return false;
        }
        for (int n = 2; n <= i/2; n++) {
            if ((i % n) == 0) {
                return false;
            }
        }
        return true;
    }
}
