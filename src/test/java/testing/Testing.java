package testing;

import org.junit.jupiter.api.Test;

public class Testing
{

    @Test
    public void test()
    {
      assert 1== min(1);
      assert 1== min(3,2,1);
      assert 1== min(1,2,3);
      assert 1== min(1,2,3,4);
      assert 1== min(1,2,3,4,5);
      assert 1== min(1,2,3,4,5,6);


    }

    static int min(int firstArgument,int... remainingArguments)
    {
        int min = firstArgument;
        for(int arg:remainingArguments)
        {
            if(arg<min)
                min = arg;
        }
        return min;
    }

    static int min1(int... args)
    {
        if(args.length ==0)
        {
            throw new IllegalArgumentException("Too few arguments");
        }

        int min = args[0];
        for(int i =1;i<args.length;i++)
        {
            if(args[i] < min)
            {
                min = args[i];
            }
        }
        return min;
    }
}
