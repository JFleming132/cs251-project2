public class FancyBlockChain {
    public Block[] bchain;
    public int length;
    public FancyBlockChain(int capacity) {
        this.bchain = Block[capacity];
        this.length = 0;
    }
    public FancyBlockChain(Block[] initialBlocks) {
        int lastNonLeaf = (initialBlocks.length / 2) - 1;
        int currIndex = 0;
        this.length = 0;
        int leftChildIndex = 0;
        int rightChildIndex = 0;
        int temp = 0;
        for (int i = lastNonLeaf; i >= 0; i--) {
            currIndex = i;
            while (true) {
                leftChildIndex = (currIndex * 2) + 1;
                rightChildIndex = (currIndex * 2) + 2;
                if ((initialBlocks.length > rightChildIndex)
                        && (initialBlocks[rightChildIndex].timestamp < initialBlocks[currIndex].timestamp)) {
                    temp = initialBlocks[currIndex];
                    initialBlocks[currIndex] = initialBlocks[rightChildIndex];
                    initialBlocks[rightChildIndex] = temp;
                    currIndex = rightChildIndex;
                } else if ((initialBlocks.length > leftChildIndex)
                        && (initialBlocks[leftChildIndex].timestamp < initialBlocks[currIndex].timestamp)) {
                    temp = initialBlocks[currIndex];
                    initialBlocks[currIndex] = initialBlocks[leftChildIndex];
                    initialBlocks[leftChildIndex] = temp;
                    currIndex = leftChildIndex;
                }
                else {
                    break;
                }
            }
        }
        this.bchain = Block[initialBlocks.length];
        for (int j = 0; j < initialBlocks.length; j++) {
            this.bchain[j] = initialBlocks[j];
            this.bchain[j].index = j;
            this.length++;
        }
    }

    public int length() {

        return this.length;
    }

    public boolean addBlock(Block newBlock) {
        int currIndex = 0;
        if (this.length() < this.bchain.length()) {
            newBlock.index = this.length();
            this.bchain[this.length()] = newBlock;
            currIndex = this.length();
            this.length++;
        }
        else if (this.bchain[0].timestamp < newBlock.timestamp) {
            this.bchain[0].removed = true;
        }
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
