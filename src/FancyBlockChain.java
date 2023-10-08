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
        //print();
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
            //System.out.println("returning true");
            return false;
        }
        if (this.length() < capacity) {
            if (newBlock.timestamp > latestTimestamp) {
                latestTimestamp = newBlock.timestamp;
            }
            newBlock.index = length();
            this.bchain[length()] = newBlock;
            length++;
            newBlock.removed = false;
            makeMinHeap();
            //print();
        }
        //System.out.println("returning false");
        return true;
    }
    public Block getEarliestBlock() {
        if (length() > 0) {
            //System.out.println("returning block with data " + bchain[0].data);
            return bchain[0];
        }
        //System.out.println("returning null");
        return null;
    }
    public Block getBlock(String data) {
        for (int i = 0; i < length(); i++) {
            if (bchain[i].data.equals(data)) {
                //System.out.println("returning block with data " + bchain[i].data);
                return bchain[i];
            }
        }
        //System.out.println("returning null");
        return null;
    }
    public Block removeEarliestBlock() {
        //print();
        //System.out.println("removing block earliest block");
        Block returnBlock = getEarliestBlock();
        if (returnBlock == null) {
            //System.out.println("returning null");
            return null;
        } else {
            bchain[returnBlock.index] = bchain[length() - 1];
            bchain[returnBlock.index].index = returnBlock.index;
            bchain[length() - 1] = null;
            length--;
            returnBlock.removed = true;
            makeMinHeap();
            //print();
            //System.out.println("returning block with data " + returnBlock.data);
            return returnBlock;
        }
    }
    public Block removeBlock(String data) {
        //print();
        //System.out.println("removing block w data" + data);
        Block returnBlock = getBlock(data);
        if (returnBlock == null) {
            //System.out.println("returning null");
            return null;
        } else {
            bchain[returnBlock.index] = bchain[length() - 1];
            bchain[returnBlock.index].index = returnBlock.index;
            bchain[length() - 1] = null;
            length--;
            returnBlock.removed = true;
            makeMinHeap();
            //print();
            //System.out.println("returning block with data " + returnBlock.data);
            return returnBlock;
        }
    }
    public void updateEarliestBlock(double nonce) {
        Block newBlock = removeEarliestBlock();
        if (newBlock != null) {
            latestTimestamp++;
            newBlock.timestamp = latestTimestamp;
            newBlock.nonce = nonce;
            addBlock(newBlock);
        }
    }
    public void updateBlock(String data, double nonce) {
        Block newBlock = removeBlock(data);
        if (newBlock != null) {
            latestTimestamp++;
            newBlock.timestamp = latestTimestamp;
            newBlock.nonce = nonce;
            addBlock(newBlock);
        }
    }

    public void makeMinHeap() { //turns the current bchain into a minHeap
        for (int i = (length()/2) - 1; i >= 0; i--) {
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
}
