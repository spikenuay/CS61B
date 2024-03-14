package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer<Integer> array = new ArrayRingBuffer<>(5);
        array.enqueue(1);
        array.enqueue(2);
        array.enqueue(3);
        array.enqueue(4);
        array.enqueue(5);
        assertEquals(1,(long)array.dequeue());
        assertEquals(2,(long)array.dequeue());
        assertEquals(3,(long)array.dequeue());
        assertEquals(4,(long)array.dequeue());
        array.enqueue(6);
        assertEquals(5,(long)array.dequeue());
        array.enqueue(7);
        assertEquals(6,(long)array.dequeue());
        assertEquals(7,(long)array.dequeue());
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> array = new ArrayRingBuffer<>(5);
        array.enqueue(1);
        array.enqueue(2);
        array.enqueue(3);
        array.enqueue(4);
        array.enqueue(5);

        for (int num: array) {
            System.out.print(num);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
