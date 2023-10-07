public class FancyBlockChain {
    public Block[] bchain;
    public int length;

    public int latestTimestamp;
    public FancyBlockChain(int capacity) {
        latestTimestamp = 0;
        this.bchain = new Block[capacity];
        this.length = 0;
    }
    public FancyBlockChain(Block[] initialBlocks) {
        latestTimestamp = 0;
        this.length = initialBlocks.length;
        this.bchain = new Block[length];
        for (int i = 0; i < length; i++) {
            bchain[i] = initialBlocks[i];
            if (bchain[i].timestamp > latestTimestamp) {
                latestTimestamp = bchain[i].timestamp;
            }
        }
        makeMinHeap();
    }

    public int length() {
        return this.length;
    }

    public boolean addBlock(Block newBlock) {
        if (this.length() < this.bchain.length) { //newblock at end
            bchain[length()] = newBlock;
            newBlock.index = length();
            makeMinHeap();
            if (newBlock.timestamp > latestTimestamp) {
                latestTimestamp = newBlock.timestamp;
            }
            return true;
        }
        else if (this.bchain[0].timestamp < newBlock.timestamp) { //newblock at start
            this.bchain[0].removed = true;
            this.bchain[0] = newBlock;
            newBlock.index = 0;
            makeMinHeap();
            if (newBlock.timestamp > latestTimestamp) {
                latestTimestamp = newBlock.timestamp;
            }
            return true;
        }
        return false;
    }
    public Block getEarliestBlock() {
        if (length() > 0) {
            return bchain[0];
        }
        return null;
    }
    public Block getBlock(String data) {
        for (int i = 0; i < length(); i++) {
            if (bchain[i].data.equals(data)) {
                return bchain[i];
            }
        }
        return null;
    }
    public Block removeEarliestBlock() {
        Block returnBlock = null;
        if (length == 1) {
            length--;
            returnBlock = bchain[0];
            bchain[0] = null;
            returnBlock.removed = true;
        }
        else if (length() > 1) {
            returnBlock = bchain[0];
            bchain[0] = bchain[length()];
            bchain[length()] = null;
            length--;
            returnBlock.removed = true;
            bchain[0].index = 0;
            makeMinHeap();
        }
        return returnBlock;
    }
    public Block removeBlock(String data) {
        Block returnBlock = getBlock(data);
        if (returnBlock == null) {
            return null;
        } else {
            bchain[returnBlock.index] = bchain[length()];
            bchain[length()] = null;
            length--;
            returnBlock.removed = true;
            bchain[returnBlock.index].index = returnBlock.index;
            makeMinHeap();
            return returnBlock;
        }
    }
    public void updateEarliestBlock(double nonce) {
        bchain[0].nonce = nonce;
        latestTimestamp++;
        bchain[0].timestamp = latestTimestamp;
        makeMinHeap();
    }
    public void updateBlock(String data, double nonce) {
        Block currentBlock = getBlock(data);
        if (currentBlock != null) {
            currentBlock.nonce = nonce;
            latestTimestamp++;
            currentBlock.timestamp = latestTimestamp;
            makeMinHeap();
        }
    }

    public void makeMinHeap() { //turns the current bchain into a minHeap
        for (int i = (length() - 1) / 2; i >= 0; i--) {
            heapify(i);
        }
    }
    public void heapify(int i) { //helper function to sink down max elements
        int largest = i;
        if (((2 * i) + 1) < length() && (bchain[i].timestamp > bchain[(i * 2) + 1].timestamp)) {
            largest = (2 * i) + 1;
        }
        if (((2 * i) + 2) < length() && (bchain[i].timestamp > bchain[(i * 2) + 2].timestamp)) {
            largest = (2 * i) + 2;
        }
        if (largest != i) {
            Block tempBlock = bchain[i];
            bchain[i] = bchain[largest];
            bchain[largest] = tempBlock;
            bchain[i].index = i;
            bchain[largest].index = largest;
        }
    }
}
