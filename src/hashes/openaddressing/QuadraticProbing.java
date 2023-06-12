package src.hashes.openaddressing;

/*------------------------------
* - Note from author:
*    I'm actually not going to waste my time coding this. It makes no sense. In terms of operation
*    comparisons, this indisputably has more operational overhead than linear probing. Everything
*    I read online for why this would EVER be used says that it's to "solve the problem of clustering"
*    in linear probing. If your hashing function is causing clustering then maybe pick a different
*    hash function? Why turn a +1 operation into chains of exponentially growing multiplication
*    operations which in actuality play out at repetitive addition calls on the CPU? This class
*    is utterly ridiculous and completely nonsensical. It doesn’t even make sense that this would
*    prevent clustering because if ten objects all get hashed to index 3, in linear probing and
*    quadratic probing you’d *still have to check all previous nine spots* for the tenth object?
*    I mean sure quadratic isn’t “locationally clustering” them but what an arbitrary thing to
*    try to avoid. Why is the focus not on how many iterations of checks it has to do… which is
*    the same with both techniques (actually worse for quadratic because you’re doing lots of
*    multiplication instead of +1).
* ------------------------------
*/

public class QuadraticProbing {}