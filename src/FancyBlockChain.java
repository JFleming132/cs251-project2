public class FancyBlockChain {
    public Block[] bchain;
    public int length;
    public FancyBlockChain(int capacity) {
        this.bchain = new Block[capacity];
        this.length = 0;
    }
    public FancyBlockChain(Block[] initialBlocks) {
        int lastNonLeaf = (initialBlocks.length / 2) - 1;
        int currIndex = 0;
        this.length = 0;
        int leftChildIndex = 0;
        int rightChildIndex = 0;
        Block tempBlock = null;
        for (int i = lastNonLeaf; i >= 0; i--) {
            currIndex = i;
            while (true) {
                leftChildIndex = (currIndex * 2) + 1;
                rightChildIndex = (currIndex * 2) + 2;
                if ((initialBlocks.length > rightChildIndex)
                        && (initialBlocks[rightChildIndex].timestamp < initialBlocks[currIndex].timestamp)) {
                    tempBlock = initialBlocks[currIndex];
                    initialBlocks[currIndex] = initialBlocks[rightChildIndex];
                    initialBlocks[rightChildIndex] = tempBlock;
                    currIndex = rightChildIndex;
                } else if ((initialBlocks.length > leftChildIndex)
                        && (initialBlocks[leftChildIndex].timestamp < initialBlocks[currIndex].timestamp)) {
                    tempBlock = initialBlocks[currIndex];
                    initialBlocks[currIndex] = initialBlocks[leftChildIndex];
                    initialBlocks[leftChildIndex] = tempBlock;
                    currIndex = leftChildIndex;
                }
                else {
                    break;
                }
            }
        }
        this.bchain = new Block[initialBlocks.length];
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
        if (this.length() < this.bchain.length) { //newblock at end
            newBlock.index = this.length();
            this.bchain[this.length()] = newBlock;
            currIndex = this.length();
            Block tempBlock = null;
            this.length++;
            while (currIndex != 0) {
                if (this.bchain[(currIndex - 1) / 2].timestamp > this.bchain[currIndex].timestamp) {
                    tempBlock = this.bchain[currIndex];
                    this.bchain[currIndex] = this.bchain[(currIndex - 1) / 2];
                    this.bchain[currIndex].index = (currIndex - 1) / 2;
                    this.bchain[(currIndex - 1) / 2] = tempBlock;
                    this.bchain[(currIndex - 1) / 2].index = currIndex;
                    currIndex = (currIndex - 1) / 2;
                } else {
                    break;
                }
            }
            return true;
        }
        else if (this.bchain[0].timestamp < newBlock.timestamp) { //newblock at start
            this.bchain[0].removed = true;
            this.bchain[0] = newBlock;
            newBlock.index = 0;
            Block tempBlock = null;
            int leftchildindex = 1;
            int rightchildindex = 2;
            while (leftchildindex < this.length()) { //while currIndex has left kid, i.e. not end of list
                if ((rightchildindex < this.length()) && //right child exists
                        (this.bchain[rightchildindex].timestamp < this.bchain[leftchildindex].timestamp) && //right key is less than left key
                        (this.bchain[currIndex].timestamp > this.bchain[rightchildindex].timestamp)) { //currIndex key is more than right key
                    //swap currIndex and right node, update currIndex
                    tempBlock = this.bchain[rightchildindex];
                    this.bchain[rightchildindex] = this.bchain[currIndex];
                    this.bchain[currIndex] = tempBlock;
                    this.bchain[currIndex].index = rightchildindex; //update index
                    this.bchain[rightchildindex].index = currIndex; //update index
                    currIndex = rightchildindex;
                } else if (this.bchain[currIndex].timestamp > this.bchain[leftchildindex].timestamp) { //currIndex key is greater than left key
                    //swap currIndex and left node, update currIndex
                    tempBlock = this.bchain[leftchildindex];
                    this.bchain[leftchildindex] = this.bchain[currIndex];
                    this.bchain[currIndex] = tempBlock;
                    this.bchain[currIndex].index = leftchildindex;
                    this.bchain[leftchildindex].index = currIndex;
                    currIndex = leftchildindex;
                }
                else { //node is in right place
                    break;
                }
                leftchildindex = (2 * currIndex) + 1; //update child pointers
                rightchildindex = (2 * currIndex) + 2;
            }
            return true;
        }
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
