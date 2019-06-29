package algorithm.string;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

public class ByteReader {
    private static PrintWriter output = new PrintWriter(System.out);
    private InputStream input;
    private byte[] buffer = new byte[1024];
    private int lenbuf = 0, ptrbuf = 0;

    ByteReader(String str) {
        input = str.isEmpty() ? System.in : new ByteArrayInputStream(str.getBytes());
    }

    private int readByte() {
        if(lenbuf == -1)
            throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try {
                lenbuf = input.read(buffer);
            } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)
                return -1;
        }
        return buffer[ptrbuf++];
    }

    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() {
        int b;
        while((b = readByte()) != -1 && isSpaceChar(b));
        return b;
    }

    private double nextDouble() { return Double.parseDouble(nextString()); }
    private char nextChar() { return (char)skip(); }

    private String nextString() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b))){ // when nextLine, (isSpaceChar(b) && b != ' ')
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }

    // Return next string with no space of at most length n
    private char[] nextString(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }

    private char[][] nextMap(int n, int m) {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)
            map[i] = nextString(m);
        return map;
    }

    private int[] nextIntArray(int n) {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = nextInteger();
        return a;
    }

    private int nextInteger() {
        return (int) nextLong();
    }

    private long nextLong() {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }

        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }

    public static void main(String[] args) {
        ByteReader byteReader = new ByteReader("100   245 1.234 \n Sleepyi h 1 2 3 catty \n d");
        output.println(byteReader.nextLong()); // 100
        output.println(byteReader.nextInteger()); // 245
        output.println(byteReader.nextDouble()); // 1.234
        output.println(byteReader.nextString()); // Sleepyi
        output.println(byteReader.nextChar());  // h
        output.println(Arrays.toString(byteReader.nextIntArray(3)));  // [1, 2, 3]
        output.println(Arrays.toString(byteReader.nextString(3)));  // [c, a, t]
        output.flush();

    }

    /*
    private static InputStream is;
    private static PrintWriter out;
    private static String INPUT = "";
    private byte[] inbuf = new byte[1024];
    public int lenbuf = 0, ptrbuf = 0;

    void solve() {

        // Read input
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        char[][] ss = new char[n][];
        for(int i = 0;i < n;i++){
            ss[i] = sc.next().toCharArray();
        }
        int[] health = new int[n];
        for(int i = 0;i < n;i++){
            health[i] = sc.nextInt();
        }

        int queries = sc.nextInt();
        char[][] strands = new char[queries][];
        long[] es = new long[2*queries];
        for(int i = 0;i < queries;i++){
            int start = sc.nextInt(), end = sc.nextInt();
            strands[i] = sc.next().toCharArray();
            es[i] = (long)start<<32|(long)i<<1|0;
            es[i+queries] = (long)end+1<<32|(long)i<<1|1;
        }

        Arrays.sort(es);
        long[] results = new long[queries];
        TrieByLink[] tries = new TrieByLink[18];
        int p = 0;
        for(long e : es){
            long x = e>>>32;
            int ind = ((int)e)>>>1;
            int pm = (e&1) == 0 ? -1 : 1;
            while(p < n && p <= x-1){
                int d = Integer.numberOfTrailingZeros(p+1);
                tries[d] = new TrieByLink();
                for(int k = p-(1<<d)+1;k <= p;k++){
                    tries[d].add(ss[k], health[k]);
                }
                tries[d].buildFailure();
                p++;
            }
            long lhit = 0;
            for(int d = 0;d < 18;d++){
                if(p<<~d<0){
                    lhit += tries[d].countHit(strands[ind]);
                }
            }
            results[ind] += lhit*pm;
        }
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        for(long r : results)min = Math.min(min, r);
        for(long r : results)max = Math.max(max, r);

        out.println(min + " " + max);
    }

    private void getTotalHealth(char[][] strands) {
        int p = 0;
        for(long e : es){
            long x = e>>>32;
            int ind = ((int)e)>>>1;
            int pm = (e&1) == 0 ? -1 : 1;
            while(p < n && p <= x-1){
                int d = Integer.numberOfTrailingZeros(p+1);
                tries[d] = new TrieByLink();
                for(int k = p-(1<<d)+1;k <= p;k++){
                    tries[d].add(ss[k], health[k]);
                }
                tries[d].buildFailure();
                p++;
            }
            long lhit = 0;
            for(int d = 0;d < 18;d++){
                if(p<<~d<0){
                    lhit += tries[d].countHit(strands[ind]);
                }
            }
            results[ind] += lhit*pm;
        }
    }

    public static class TrieByLink {
        public Node root = new Node((char)0, 0);
        public int gen = 1;
        public static final char[] atoz = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        public static class Node
        {
            public int id;
            public char c;
            public Node next, firstChild;
            public long hit = 0;

            public Node fail;

            public Node(char c, int id)
            {
                this.id = id;
                this.c = c;
            }

            public String toString(String indent)
            {
                StringBuilder sb = new StringBuilder();
                sb.append(indent + id + ":" + c);
                if(hit != 0)sb.append(" H:" + hit);
                if(fail != null)sb.append(" F:" + fail.id);
                sb.append("\n");
                for(Node c = firstChild;c != null; c = c.next){
                    sb.append(c.toString(indent + "  "));
                }
                return sb.toString();
            }
        }

        public void add(char[] s, long hit)
        {
            Node cur = root;
            Node pre = null;
            for(char c : s){
                pre = cur; cur = cur.firstChild;
                if(cur == null){
                    cur = pre.firstChild = new Node(c, gen++);
                }else{
                    for(;cur != null && cur.c != c;pre = cur, cur = cur.next);
                    if(cur == null)cur = pre.next = new Node(c, gen++);
                }
            }
            cur.hit += hit;
        }

        public void buildFailure()
        {
            root.fail = null;
            Queue<Node> q = new ArrayDeque<>();
            q.add(root);
            while(!q.isEmpty()){
                Node cur = q.poll();
                inner:
                for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                    q.add(ch);
                    for(Node to = cur.fail; to != null; to = to.fail){
                        for(Node lch = to.firstChild;lch != null; lch = lch.next){
                            if(lch.c == ch.c){
                                ch.fail = lch;
                                ch.hit += lch.hit; // propagation of hit
                                continue inner;
                            }
                        }
                    }
                    ch.fail = root;
                }
            }
        }

        public Node next(Node cur, char c)
        {
            for(;cur != null;cur = cur.fail){
                for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                    if(ch.c == c)return ch;
                }
            }
            return root;
        }

        public int[][] ngMatrix(char[] cs)
        {
            int[] map = new int[128];
            Arrays.fill(map, -1);
            for(int i = 0;i < cs.length;i++)map[cs[i]] = i;

            int[][] a = new int[gen+1][gen+1];
            Node[] nodes = toArray();
            for(int i = 0;i < gen;i++){
                if(nodes[i].hit > 0)continue;
                int nohit = cs.length;
                boolean[] ved = new boolean[cs.length];
                for(Node cur = nodes[i];cur != null;cur = cur.fail){
                    for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                        if(map[ch.c] >= 0 && !ved[map[ch.c]]){
                            ved[map[ch.c]] = true;
                            if(ch.hit == 0)a[ch.id][i]++;
                            nohit--;
                        }
                    }
                }
                a[0][i] += nohit;
            }
            Arrays.fill(a[gen], 1);
            return a;
        }

        public int[][] okMatrix(char[] cs)
        {
            int[] map = new int[128];
            Arrays.fill(map, -1);
            for(int i = 0;i < cs.length;i++)map[cs[i]] = i;

            int[][] a = new int[gen+1][gen+1];
            Node[] nodes = toArray();
            for(int i = 0;i < gen;i++){
                if(nodes[i].hit > 0)continue;
                int nohit = cs.length;
                boolean[] ved = new boolean[cs.length];
                for(Node cur = nodes[i];cur != null;cur = cur.fail){
                    for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                        if(map[ch.c] >= 0 && !ved[map[ch.c]]){
                            ved[map[ch.c]] = true;
                            if(ch.hit > 0){
                                a[gen][i]++;
                            }else{
                                a[ch.id][i]++;
                            }
                            nohit--;
                        }
                    }
                }
                a[0][i] += nohit;
            }
            a[gen][gen]++;
            return a;
        }

        public void search(char[] q)
        {
            Node cur = root;
            outer:
            for(char c : q){
                for(;cur != null;cur = cur.fail){
                    for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                        if(ch.c == c){
                            // ch.hit
                            cur = ch;
                            continue outer;
                        }
                    }
                }
                cur = root;
            }
        }

        public long countHit(char[] q)
        {
            Node cur = root;
            long hit = 0;
            outer:
            for(char c : q){
                for(;cur != null;cur = cur.fail){
                    for(Node ch = cur.firstChild;ch != null; ch = ch.next){
                        if(ch.c == c){
                            hit += ch.hit; // add hit
                            cur = ch;
                            continue outer;
                        }
                    }
                }
                cur = root;
            }
            return hit;
        }

        public Node[] toArray()
        {
            Node[] ret = new Node[gen];
            ret[0] = root;
            for(int i = 0;i < gen;i++){
                Node cur = ret[i];
                if(cur.next != null)ret[cur.next.id] = cur.next;
                if(cur.firstChild != null)ret[cur.firstChild.id] = cur.firstChild;
            }
            return ret;
        }

        public String toString()
        {
            return root.toString("");
        }
    }

    void run() throws Exception
    {
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        out = new PrintWriter(System.out);

        long s = System.currentTimeMillis();
        solve();
        out.flush();
    }

    public static void main(String[] args) throws Exception { new DeterminingDNAHealth().run(); } */
}
