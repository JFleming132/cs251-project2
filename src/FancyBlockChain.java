public class FancyBlockChain {
    public Block[] bchain;

    public FancyBlockChain(int capacity) {
    }
    public FancyBlockChain(Block[] initialBlocks) {
    }

    public int length() {
        return 0;
    }

    public boolean addBlock(Block newBlock) {
        return false;
    }
    public Block getEarliestBlock() {
        return null;
    }
    public Block getBlock(String data) {
        return null;
    }
    public Block removeEarliestBlock() {
        return null;
    }
    public Block removeBlock(String data) {
        return null;
    }
    public void updateEarliestBlock(double nonce) {
    }
    public void updateBlock(String data, double nonce) {
    }
}
