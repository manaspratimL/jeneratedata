# Introduction #

jeneratedata is a set of utility classes to generate random test data. Starting
from the Generator interface that defines the following (generic) method
```
<T> T generate();
```
The implementing classes, through inheritance and composition, build up to create a rich set of tools to generate all kinds of random data.

# Requirements #

The only requirement so far is Java 1.5, due to the heavy use of generics. If you need to use this library with an older version of Java, send me an email and I'll port it (it's really easy).

I've integrated [RandomStringUtils](http://commons.apache.org/lang/api-release/org/apache/commons/lang/RandomStringUtils.html) from the [Commons Lang](http://commons.apache.org/lang/) project. It seemed a little overkill to add the whole library as a dependency just for this (AWESOME) class.

# Motivation #

The motivation for this package came from requirements that I had in a couple of projects at my current job.

The first requirement was to test a program that handled CSV-like files with large data sets that included all kinds of data types. To solve this problem I used [generatedata.com](http://www,generatedata.com) to get the raw data, and then processed it using Java.

The second project needed test data as well, but it had to look like real data. This data would be used to fill some UI controls (the project uses [ExtGWT](http://www.extjs.com/products/gwt/) and [Google Web Toolkit](http://code.google.com/webtoolkit/)) that didn't had proper data sources. This was really important since we needed to show a potential client what the application would look like when it's finished and we didn't want it to look half-baked. We used [generatedata.com](http://www.generatedata.com) once again, and embedded the data in Java data source classes (statically).

Having some spare time, and loving to contribute to Free Software projects, I thought that I should put the lessons learned to good use.

# Usage #

In its current state jeneratedata requires the user to be explicit on the way the generators are built (composed). In the (hopefully near) future, I'll add builders to make things easier.

Suppose we need to generate a CSV file with random data about seemingly real people. The file has seven columns:

| **Name** | **Description** |
|:---------|:----------------|
| Id       | A numerical sequence to identify each record. |
| Name     | The full name of the person. |
| Birthdate | The date of birth of the person. |
| Balance  | The current bank account balance. |
| FoodPreference |  The food preference that can be "Red meat", "Chicken" or "None" (those pesky vegans). |
| Answer   | A fixed column that should always contain the value 42. |

Before we start, let's take a look at the hear of jeneratedata, the `Generator` interface:
```
    public interface Generator<T> {
        T generate();
    }
```
This says that a `Generator` should create a data item of type T each time we call to it's generate implementation. This method could give always the same answer, or random one, or take it from a list of values, etc.

For our example we would need something like a `Generator<CSVFile>` instance, but sadly no such thing exists (yet). If we think a little, we notice that a CSV file could be regarded as a list of rows (strings), so we could implement something like a `Generator<List<String>>`, but I think it'll be easier to try building each row first, one column data item at a time.

A typical row could look like the following:
```
34,Peter Griffin,10/26/1972,$23,Red meat,42
```
Let's see how we can generate each column data item. For the "Id" we already have `LongSequenceGenerator` which is a `Generator<Long>`, every call to `generate()` will return a `Long` number following a numerical sequence. Let's say we want the 0, 1, 2, 3, ... natural numerical sequence:
```
    Generator<Long> idGenerator = new LongSequenceGenerator(0L, 1L);
```
We don't have a `Generator` for full names, so we'll have to build one using `MaleNameGenerator`, `FemaleNameGenerator` and `LastNameGenerator`.
```
    Generator<String> nameGenerator = new Generator<String>() {
        private Random random = new Random();
        private Generator<String> maleNameGenerator = new MaleNameGenerator();
        private Generator<String> femaleNameGenerator = new FemaleNameGenerator();
        private Generator<String> lastNameGenerator = new LastNameGenerator();
	
        @Override
        public String generate() {
            if (random.nextDouble() < 0.5) {
                return maleNameGenerator.generate() + " " + lastNameGenerator.generate();
            } else {
                return femaleNameGenerator.generate() + " " + lastNameGenerator.generate();
            }
        }
    };
```
This generator will give (approximately) the same number of male and female names.

The date of birth could be generated using `CurrentDateGenerator` which gives the current date on each call to `generate()`, but for this example we're restricting the dates to those on the 1970s; so we'll use the more general `DateGenerator`:
```
    Calendar start = Calendar.getInstance();
    start.clear();
    start.set(Calendar.MONTH, Calendar.JANUARY);
    start.set(Calendar.DAY_OF_MONTH, 1);
    start.set(Calendar.YEAR, 1970);
	
    Calendar end = Calendar.getInstance();
    end.clear();
    end.set(Calendar.MONTH, Calendar.DECEMBER);
    end.set(Calendar.DAY_OF_MONTH, 31);
    end.set(Calendar.YEAR, 1979);
    end.set(Calendar.HOUR_OF_DAY, 23);
    end.set(Calendar.MINUTE, 59);
    end.set(Calendar.SECOND, 59);
	
    Generator<Date> birthDateGenerator = new DateGenerator(start, end);
```
But we specified that our birth date column should have a specific format, so we'll use a `FormattedDateGenerator` instead:
```
    Generator<String> birthDateGenerator = new FormattedDateGenerator(
        new DateGenerator(start, end),
        new SimpleDateFormat("MM/dd/yyyy")
    );
```
To generate the account balance, we have a couple of options. We could use a `NumberStringGenerator` with a `MessageFormatBasedGenerator`, but I think we should go for a composition of `FormattedNumberGenerator` and `IntegerGenerator` (if you need a `LongGenerator` or a `BigInteger` for your account balance, I HATE YOU :).
```
    Generator<String> accountBalanceGenerator = new FormattedNumberGenerator<Integer>(
       new IntegerGenerator(),
       NumberFormat.getCurrencyInstance()
   );
```
One more time we have several options (I like options) to build the "FoodPreference" column. We could use some form of array based generator or list based generator. Those families of generators behave similarly choosing (using several strategies) the generated data item from within the elements of the supplied data structure. We'll use an array based generator that chooses randomly between its elements:
```
    Generator<String> foodPreferenceGenerator = new RandomSequenceArrayBasedGenerator<String>("Red meat", "Chicken", "None");
```
The last column is easy, being just a fixed value, we'll use a `FixedValueGenerator`:
```
    Generator<String> answerGenerator = new FixedValueGenerator<String>("42");
```
given that the answer is always a number we could have also used:
```
    Generator<Integer> answerGenerator = new FixedValueGenerator<Integer>(42);
```
To generate each row, we'll combine all generators using a `MultiGenerator`:
```
    Generator<Object[]> rowDataGenerator = new MultiGenerator(
        idGenerator,
        nameGenerator,
        birthDateGenerator,
        accountBalanceGenerator,
        foodPreferenceGenerator,
        answerGenerator
    );
```
Each time we call `rowGenerator.generate()` we'll get an array that contains the result of calling `generate()` on each of the generators and the result stored under the corresponding index.

We can combine these values into a single string using [Commons Lang](http://commons.apache.org/lang/)'s http://commons.apache.org/lang/api-release/org/apache/commons/lang/StringUtils.html#join%28java.lang.Object[,%20char%29 StringUtils.join()]:
```
    Generator<String> rowGenerator = new Generator<String>() {
        @Override
        public String generate() {
            StringUtils.join(rowDataGenerator.generate(), ',');
        }
    };
```
But that could be a problem since the account balance (depending on the locale) might have commas on it, which clashes with our separator. One possible solution is to use a `MessageFormatBasedGenerator`:
```
    Generator<String> rowGenerator = new MessageFormatBasedGenerator(
        "{0},{1},{2},\"{3}\",{4},{5}",
        rowDataGenerator
    );
```
I hope this give you an idea of what jeneratedata can do. In the future I'll
add more features. If you have suggestions, please don't hesitate to create
an issue on the [project's page](http://code.google.com/p/jeneratedata).