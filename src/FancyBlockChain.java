public class FancyBlockChain {
    public Block[] bchain;
    public int length;

    public int capacity;
    public int latestTimestamp;
    public FancyBlockChain(int capacity) {
        latestTimestamp = 0;
        this.capacity = capacity;
        this.bchain = new Block[capacity];
        this.length = 0;
    }
    public FancyBlockChain(Block[] initialBlocks) {
        latestTimestamp = 0;
        capacity = initialBlocks.length;
        this.bchain = new Block[capacity];
        for (Block initialBlock : initialBlocks) {
            addBlock(initialBlock);
        }
        print();
    }

    public int length() {
        return this.length;
    }

    public boolean addBlock(Block newBlock) {
        //print();
        //System.out.println("Adding block " + newBlock.timestamp);
        if ((length() == capacity) && (bchain[0].timestamp < newBlock.timestamp)) {
            removeEarliestBlock();
        }
        else if (length == capacity) {
            return false;
        }
        if (this.length() < capacity) {
            if (newBlock.timestamp > latestTimestamp) {
                latestTimestamp = newBlock.timestamp;
            }
            newBlock.index = length();
            this.bchain[length()] = newBlock;
            length++;
            makeMinHeap();
            //print();
        }
        return true;
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
        //print();
        Block returnBlock = null;
        if (length == 1) {
            length--;
            returnBlock = bchain[0];
            bchain[0] = null;
            returnBlock.removed = true;
        }
        else if (length() > 1) {
            returnBlock = bchain[0];
            bchain[0] = bchain[length() - 1];
            bchain[length() - 1] = null;
            length--;
            returnBlock.removed = true;
            bchain[0].index = 0;
            makeMinHeap();
        }
        //print();
        return returnBlock;
    }
    public Block removeBlock(String data) {
        //print();
        //System.out.println("removing block w data" + data);
        Block returnBlock = getBlock(data);
        if (returnBlock == null) {
            //System.out.println("no such block");
            return null;
        } else {
            if (returnBlock.index != (length() - 1)) {
                bchain[returnBlock.index] = bchain[length() - 1];
            }
            bchain[length() - 1] = null;
            length--;
            returnBlock.removed = true;
            makeMinHeap();
            //print();
            return returnBlock;
        }
    }
    public void updateEarliestBlock(double nonce) {
        bchain[0].nonce = nonce;
        latestTimestamp++;
        bchain[0].timestamp = latestTimestamp;
        Block tempBlock = bchain[length() - 1];
        bchain[length() - 1] = bchain[0];
        bchain[0] = tempBlock;
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
        int minimum = i;
        if (((2 * i) + 1) < length() && (bchain[minimum].timestamp > bchain[(i * 2) + 1].timestamp)) {
            minimum = (2 * i) + 1;
        }
        if (((2 * i) + 2) < length() && (bchain[minimum].timestamp > bchain[(i * 2) + 2].timestamp)) {
            minimum = (2 * i) + 2;
        }
        if (minimum != i) {
            Block tempBlock = bchain[i];
            bchain[i] = bchain[minimum];
            bchain[minimum] = tempBlock;
            bchain[i].index = i;
            bchain[minimum].index = minimum;
            heapify(minimum);
        }
    }

    public void print() {
        System.out.print("The heap has length " + length() + ", capacity " + capacity + ", max timestamp is " + latestTimestamp + ", and is: ");
        for (int i = 0; i < length(); i++) {
            System.out.print(bchain[i].timestamp + " ");
        }
        System.out.println();
    }
}
