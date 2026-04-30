import java.util.Arrays;

public class TreeIsomorphism {
    private final Graph graph;
    private String validationMessage;
    private Boolean tree;
    private int[] centers;
    private String canonicalEncoding;

    public TreeIsomorphism(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isTree() {
        if (tree != null) {
            return tree;
        }

        int vertices = graph.V();
        int edges = graph.E();

        if (vertices == 0) {
            tree = false;
            validationMessage = "Entrada invalida: grafo sem vertices nao representa uma arvore.";
            return false;
        }

        if (edges != vertices - 1) {
            tree = false;
            validationMessage = "Entrada invalida: para ser arvore, o grafo deve ter E = V - 1. V = "
                    + vertices + ", E = " + edges + ".";
            return false;
        }

        boolean[] visited = new boolean[vertices];
        dfsConnectivity(0, visited);

        for (int v = 0; v < vertices; v++) {
            if (!visited[v]) {
                tree = false;
                validationMessage = "Entrada invalida: o grafo nao e conexo; o vertice " + v + " nao foi alcancado.";
                return false;
            }
        }

        tree = true;
        validationMessage = "Entrada valida: o grafo e uma arvore.";
        return true;
    }

    public String getValidationMessage() {
        isTree();
        return validationMessage;
    }

    public int[] getCenters() {
        if (centers != null) {
            return centers.clone();
        }

        if (!isTree()) {
            centers = new int[0];
            return centers.clone();
        }

        int vertices = graph.V();

        if (vertices == 1) {
            centers = new int[]{0};
            return centers.clone();
        }

        int[] degree = new int[vertices];
        Bag<Integer> leaves = new Bag<Integer>();

        for (int v = 0; v < vertices; v++) {
            degree[v] = graph.degree(v);
            if (degree[v] <= 1) {
                leaves.add(v);
            }
        }

        int processed = leaves.size();

        while (processed < vertices) {
            Bag<Integer> newLeaves = new Bag<Integer>();

            for (int leaf : leaves) {
                for (int neighbor : graph.adj(leaf)) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 1) {
                        newLeaves.add(neighbor);
                    }
                }
            }

            processed += newLeaves.size();
            leaves = newLeaves;
        }

        centers = bagToSortedArray(leaves);
        return centers.clone();
    }

    public String getCanonicalEncoding() {
        if (canonicalEncoding != null) {
            return canonicalEncoding;
        }

        if (!isTree()) {
            canonicalEncoding = "";
            return canonicalEncoding;
        }

        int[] treeCenters = getCenters();
        String[] encodings = new String[treeCenters.length];

        for (int i = 0; i < treeCenters.length; i++) {
            encodings[i] = encode(treeCenters[i], -1);
        }

        Arrays.sort(encodings);
        canonicalEncoding = encodings[0];
        return canonicalEncoding;
    }

    private void dfsConnectivity(int vertex, boolean[] visited) {
        visited[vertex] = true;

        for (int neighbor : graph.adj(vertex)) {
            if (!visited[neighbor]) {
                dfsConnectivity(neighbor, visited);
            }
        }
    }

    private String encode(int vertex, int parent) {
        Bag<String> childCodes = new Bag<String>();

        for (int neighbor : graph.adj(vertex)) {
            if (neighbor != parent) {
                childCodes.add(encode(neighbor, vertex));
            }
        }

        if (childCodes.isEmpty()) {
            return "()";
        }

        String[] codes = bagToArray(childCodes);
        Arrays.sort(codes);

        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (String code : codes) {
            builder.append(code);
        }
        builder.append(")");

        return builder.toString();
    }

    private int[] bagToSortedArray(Bag<Integer> bag) {
        int[] array = new int[bag.size()];
        int index = 0;

        for (int value : bag) {
            array[index] = value;
            index++;
        }

        Arrays.sort(array);
        return array;
    }

    private String[] bagToArray(Bag<String> bag) {
        String[] array = new String[bag.size()];
        int index = 0;

        for (String value : bag) {
            array[index] = value;
            index++;
        }

        return array;
    }
}
