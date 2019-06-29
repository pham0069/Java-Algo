package graph.minimumSpanningTree;

/**
 * https://www.geeksforgeeks.org/minimum-product-spanning-tree/
 * https://stackoverflow.com/questions/19358970/is-the-minimum-product-spanning-tree-different-from-a-minimum-sum-spanning-tree
 *
 * Minimum-product spanning tree is the spanning tree with least product of all the tree edges.
 * Minimum-product spanning tree is also minimum spanning tree, if all the edges have positive weight
 * Thus MPST can be found using Kruskal or Prim algorithm.
 *
 * This can be proved based on the following logarithm properties:
 * log (e1*e2*...*en) = log(e1) + log(e2) + ... + log(en)
 * e1 < e2 <-> log(e1) < log(e2)
 *
 * The difference comes to the picture if the graph has non-positive-weight edges
 */
public class MinimumProductSpanningTree {
}
