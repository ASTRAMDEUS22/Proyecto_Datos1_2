package Clases_auxiliares;

public class AVLTree {
    private AVLNode root;
    public void insert(int value) {
        AVLNode newNode = new AVLNode(value);
        root = insert(root, newNode);
    }

    private AVLNode insert(AVLNode node, AVLNode newNode) {
        if (node == null) {
            return newNode;
        }

        if (newNode.getValue() < node.getValue()) {
            node.setLeft(insert(node.getLeft(), newNode));
        } else {
            node.setRight(insert(node.getRight(), newNode));
        }

        update(node);

        int balanceFactor = node.getBalanceFactor();

        if (balanceFactor > 1 && newNode.getValue() < node.getLeft().getValue()) {
            return rotateRight(node);
        }

        if (balanceFactor > 1 && newNode.getValue() > node.getLeft().getValue()) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        if (balanceFactor < -1 && newNode.getValue() > node.getRight().getValue()) {
            return rotateLeft(node);
        }

        if (balanceFactor < -1 && newNode.getValue() < node.getRight().getValue()) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);
        update(node);
        update(newRoot);
        return newRoot;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);
        update(node);
        update(newRoot);
        return newRoot;
    }

    private void update(AVLNode node) {
        int leftHeight = node.getLeft() != null ? node.getLeft().getHeight() : 0;
        int rightHeight = node.getRight() != null ? node.getRight().getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }
    public void delete(int value) {
        root = delete(root, value);
    }

    private AVLNode delete(AVLNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.getValue()) {
            node.setLeft(delete(node.getLeft(), value));
        } else if (value > node.getValue()) {
            node.setRight(delete(node.getRight(), value));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() == null || node.getRight() == null) {
                AVLNode child = node.getLeft() != null ? node.getLeft() : node.getRight();
                child.setHeight(node.getHeight() - 1);
                return child;
            } else {
                AVLNode successor = findSuccessor(node.getRight());
                node.setValue(successor.getValue());
                node.setRight(delete(node.getRight(), successor.getValue()));
            }
        }

        update(node);

        int balanceFactor = node.getBalanceFactor();

        if (balanceFactor > 1 && node.getLeft().getBalanceFactor() >= 0) {
            return rotateRight(node);
        }

        if (balanceFactor > 1 && node.getLeft().getBalanceFactor() < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        if (balanceFactor < -1 && node.getRight().getBalanceFactor() <= 0) {
            return rotateLeft(node);
        }

        if (balanceFactor < -1 && node.getRight().getBalanceFactor() > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode findSuccessor(AVLNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }
    public AVLNode find(int value) {
        AVLNode current = root;
        while (current != null) {
            if (current.getValue() == value) {
                return current;
            } else if (value < current.getValue()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }
    public void printTree() {
        printTree(root);
    }

    private void printTree(AVLNode node) {
        if (node == null) {
            return;
        }
        printTree(node.getLeft());
        System.out.print(node.getValue() + " ");
        printTree(node.getRight());
    }

}
