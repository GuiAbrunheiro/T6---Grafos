import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "informe dois arquivos de entrada. Ex.: java Main ../dados/arvore1.txt ../dados/arvore2.txt"
            );
        }

        Graph tree1 = new Graph(new In(args[0]));
        Graph tree2 = new Graph(new In(args[1]));

        StdOut.println("========================================");
        StdOut.println("T6 - Isomorfismo em Arvores");
        StdOut.println("========================================");
        StdOut.println();

        StdOut.println("Arvore 1 - lista de adjacencia:");
        StdOut.println(tree1);
        StdOut.println();

        StdOut.println("Arvore 2 - lista de adjacencia:");
        StdOut.println(tree2);
        StdOut.println();

        TreeIsomorphism analysis1 = new TreeIsomorphism(tree1);
        TreeIsomorphism analysis2 = new TreeIsomorphism(tree2);

        StdOut.println("Validacao da entrada 1:");
        StdOut.println(analysis1.getValidationMessage());
        StdOut.println();

        StdOut.println("Validacao da entrada 2:");
        StdOut.println(analysis2.getValidationMessage());
        StdOut.println();

        if (!analysis1.isTree() || !analysis2.isTree()) {
            StdOut.println("Comparacao interrompida: as duas entradas precisam representar arvores validas.");
            return;
        }

        StdOut.println("Centros da arvore 1: " + Arrays.toString(analysis1.getCenters()));
        StdOut.println("Centros da arvore 2: " + Arrays.toString(analysis2.getCenters()));
        StdOut.println();

        String encoding1 = analysis1.getCanonicalEncoding();
        String encoding2 = analysis2.getCanonicalEncoding();

        StdOut.println("Codificacao canonica da arvore 1:");
        StdOut.println(encoding1);
        StdOut.println();

        StdOut.println("Codificacao canonica da arvore 2:");
        StdOut.println(encoding2);
        StdOut.println();

        StdOut.println("Veredito final:");
        if (encoding1.equals(encoding2)) {
            StdOut.println("As arvores sao isomorfas.");
        } else {
            StdOut.println("As arvores nao sao isomorfas.");
        }
    }
}
