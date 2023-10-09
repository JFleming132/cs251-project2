public class BlockChainSecure {
    public FancyBlockChain fbc;
    public Block[] btable;

    public BlockChainSecure(int capacity) {
        fbc = new FancyBlockChain(capacity); //initialize fbc
        int n = fbc.capacity + 1; //size of btable should be next prime
        while (isNotPrime(n)) { //get next prime
            n++;
        }
        btable = new Block[n]; //initialize table
    }
    public BlockChainSecure(Block[] initialBlocks) {
        fbc = new FancyBlockChain(initialBlocks); //initialize fbc
        int n = fbc.capacity + 1; //size of btable should be next prime
        while (isNotPrime(n)) { //get next prime
            n++;
        }
        btable = new Block[n]; //initialize table
        for (Block initialBlock : initialBlocks) { //add each element
            addBlockFromConstructor(initialBlock); //using special array that does not touch fbc since its already done
        }
    }

    public int length() {
        return fbc.length();
    } //copied from fbc

    public boolean addBlock(Block newBlock) {
        if (fbc.length == fbc.capacity) { //if no room in fbc for newBlock
            if (newBlock.timestamp > getEarliestBlock().timestamp) { //if newBlock should replace earliest
                removeEarliestBlock(); //remove earliest
            }
            else { //newBlock should be discarded
                return false; //abort addBlock operation
            }
        }
        int firstHashIndex = Hasher.hash1(newBlock.data, btable.length) % btable.length; //first index to hash
        int currentHashIndex = firstHashIndex; //set iterative bookmark
        do {
            if ((btable[currentHashIndex] == null) || (btable[currentHashIndex].removed)) { //if currentHashIndex is available
                insertAtIndex(newBlock, currentHashIndex, true); //insert into table at index, updating fbc
                return true;
            }
            currentHashIndex += Hasher.hash2(newBlock.data, btable.length); //move to next index
            currentHashIndex %= btable.length; //wrap around table
        } while (firstHashIndex != currentHashIndex); //stop once we've looped once
        do { //repeat procedure, incrementing by 1 instead of hash2(data, length)
            if ((btable[currentHashIndex] == null) || (btable[currentHashIndex].removed)) {
                insertAtIndex(newBlock, currentHashIndex, true);
                return true;
            }
            currentHashIndex += 1;
            currentHashIndex %= btable.length;
        } while (firstHashIndex != currentHashIndex);
        return false; //we should never hit this line because there will always be space in btable if there is space in bchain because |btable| > |bchain|
    }

    public void insertAtIndex(Block newBlock, int currentHashIndex, boolean updateFBC) { //used for abstraction
        if (updateFBC) { //if not inserting from constructor
            fbc.addBlock(newBlock); //add to fbc
        }
        btable[currentHashIndex] = newBlock; //assign to index, which was previously checked for availability
    }
    public void addBlockFromConstructor(Block newBlock) { //identical to addblock but skips fbc capacity check and does not update fbc with each addition
        int firstHashIndex = Hasher.hash1(newBlock.data, btable.length) % btable.length;
        int currentHashIndex = firstHashIndex;
        do {
            if ((btable[currentHashIndex] == null) || (btable[currentHashIndex].removed)) {
                insertAtIndex(newBlock, currentHashIndex, false);
                return;
            }
            currentHashIndex += Hasher.hash2(newBlock.data, btable.length);
            currentHashIndex %= btable.length;
        } while (firstHashIndex != currentHashIndex);
        do {
            if ((btable[currentHashIndex] == null) || (btable[currentHashIndex].removed)) {
                insertAtIndex(newBlock, currentHashIndex, false);
                return;
            }
            currentHashIndex += 1;
            currentHashIndex %= btable.length;
        } while (firstHashIndex != currentHashIndex);
    }
    public Block getEarliestBlock() { //copied from fbc
        return fbc.getEarliestBlock(); //quicker to search the chain since it will always be bchain[0];
    }
    public Block getBlock(String data) { //identical to addblock but returns the block we're interested in instead of adding block, or null if it hits a null slot
        int firstHashIndex = Hasher.hash1(data, btable.length) % btable.length;
        int currentHashIndex = firstHashIndex;
        do {
            if ((btable[currentHashIndex] != null) && (!btable[currentHashIndex].removed) && (btable[currentHashIndex].data.equals(data))) {
                return btable[currentHashIndex];
            }
            if (btable[currentHashIndex] == null) {
                return null;
            }
            currentHashIndex += Hasher.hash2(data, btable.length);
            currentHashIndex %= btable.length;
        } while (firstHashIndex != currentHashIndex);
        do {
            if ((btable[currentHashIndex] != null) && (!btable[currentHashIndex].removed) && (btable[currentHashIndex].data.equals(data))) {
                return btable[currentHashIndex];
            }
            if (btable[currentHashIndex] == null) {
                return null;
            }
            currentHashIndex += 1;
            currentHashIndex %= btable.length;
        } while (firstHashIndex != currentHashIndex);
        return null;
    }

    public Block removeEarliestBlock() { //copied from fbc
        return fbc.removeEarliestBlock(); //sets removed flag to true on the block, removing it from btable too
    }
    public Block removeBlock(String data) {
        Block returnBlock = getBlock(data); //BlockChainSecure getBlock os O(1) so we use it instead
        if (returnBlock == null) { //does the block we want not actually exist?
            return null; //abort operation
        }
        fbc.removeBlock(returnBlock.index); //removing a block by index is O(1), and removed flag gets set too
        return returnBlock;
    }
    public void updateEarliestBlock(double nonce) {
        Block newBlock = getEarliestBlock(); //peek earliest block
        if (newBlock != null) { //if that block exists
            fbc.latestTimestamp++; //update max timestamp
            newBlock.timestamp = fbc.latestTimestamp; //update block timestamp
            newBlock.nonce = nonce; //update block nonce
            fbc.makeMinHeap(); //reorder heap
        }
    }
    public void updateBlock(String data, double nonce) {
        Block newBlock = getBlock(data); //peek specific block
        if (newBlock != null) { //if block exists
            fbc.latestTimestamp++; //update timestamp
            newBlock.timestamp = fbc.latestTimestamp; //update block timestamp
            newBlock.nonce = nonce; //update block nonce
            fbc.makeMinHeap(); //reorder heap
        }
    }

    public boolean isNotPrime(int i) {
        if (i <= 1) { //if i is 1 or less
            return true; //its not prime
        }
        for (int n = 2; n <= i/2; n++) { //for each integer between 2 and i/2
            if ((i % n) == 0) { //if i divided by that integer is zero
                return true; //i has a factor, and is not prime
            }
        }
        return false; //since no integer has a factor greater than itself / 2, we can safely say i is prime
    }
}
