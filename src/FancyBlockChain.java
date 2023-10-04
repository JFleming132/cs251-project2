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
        if (this.length() < this.bchain.length()) { //newblock at end
            newBlock.index = this.length();
            this.bchain[this.length()] = newBlock;
            currIndex = this.length();
            this.length++;
        }
        else if (this.bchain[0].timestamp < newBlock.timestamp) { //newblock at start
            this.bchain[0].removed = true;
            this.bchain[0] = newBlock;
            newBlock.index = 0;
            Block tempBlock = null;
            int leftchildindex = (2 * currIndex) + 1;
            int rightchildindex = (2 * currIndex) + 2;
            while (leftchildindex < this.length()) { //while currIndex has left kid, i.e. not end of list
                if ((rightchildindex < this.length()) && //right child exists
                        (this.bchain[rightchildindex] < this.bchain[leftchildindex]) && //right key is less than left key
                        (this.bchain[currIndex] > this.bchain[rightchildindex])) { //currIndex key is more than right key
                    //swap currIndex and right node, update currIndex
                    tempBlock = this.bchain[rightchildindex];
                    this.bchain[rightchildindex] = this.bchain[currIndex];
                    this.bchain[currIndex] = tempBlock;
                    this.bchain[currIndex].index = rightchildindex; //update index
                    this.bchain[rightchildindex].index = currIndex; //update index
                    currIndex = rightchildindex;
                } else if (this.bchain[currIndex] > this.bchain[leftchildindex]) { //currIndex key is greater than left key
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
        }
        else { //newblock dont fit
            return false;
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
