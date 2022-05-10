# nc-java-lab4
**Замечание: все измерения сделаны с помощью класса Instant с небольшим количеством прогонов (4), а значит, не являются абсолютно точными. Присутствует погрешность.**
### Сравнение LinkedList и MyLinkedList
![Альтернативный текст](https://sun7-9.userapi.com/s/v1/if2/0mV2zcWwRPXIIZStiEjdCZ-jyqWEJ3I5C_e6U4MFcW_yFi5UM5GiRtUnEQcohkMaB9G4u_yfVpGwRN8KDni0Kw85.jpg?size=874x298&quality=96&type=album)
#### Вставка
1. Вставка в начало
+  Выполляется за o(1) в обоих случаях.
2. Вставка в середину
+ Выполняется в **MyLinkedList** незначительно быстрее.
3. Вставка в конец
+ Выполняется за o(1) в обоих случаях.

#### Удаление
1. Удаление из начала
+ Выполняется за o(1) в обоих случаях.
2. Удаление из середины
+ Выполняется в **MyLinkedList** незначительно быстрее.
3. Удаление из конца
+ Выполняется за o(1) в **LinkedList**, так как это двусвязный список => можно получить last.prev и сделать его последним элементом за несколько операций.
+ Выполняется пропорционально количеству элементов в **MyLinkedList**, так как это односвязный список => нужно пройтись по всем элементам, чтобы дойти до предпоследнего.

#### Поиск
1. Поиск по индексу
+ Выполняется пропорционально количеству элементов в обоих случаях.
+ Выполняется в LinkedList незначительно быстрее.

#### Вывод
*Собственная реализация связного списка оказалась по производительности сопоставима с **LinkedList** из java.util, 
за исключением удаления элементов из конца, где производительность собственной реализации значительно проигрывает стандартной.*


### Сравнение ArrayList и LinkedList
![Альтернативный текст](https://sun7-13.userapi.com/s/v1/if2/p-5K4fACCWMGAVrsPzJluGHfCGcH79_4I0TjPtQST40YCdQS5T-qfJxOVrrqZgt0PqUJelT08R9qCutO5boNuQo8.jpg?size=874x415&quality=96&type=album)
#### Вставка + удаление
1. Вставка в начало
+ Выполняется в **ArrayList** пропорционально количеству элементов.
+ Выполняется за o(1) в **LinkedList**.
2. Вставка в середину
+ Выполняется пропорционально количеству элементов в обоих случаях.
+ Выполняется в **ArrayList** значительно быстрее. Скорее всего, это достигается за счет выполнения нативного метода arraycopy для копирования массивов.
3. Вставка в конец
+ Выполняется за o(1) в обоих случаях.

#### Поиск
1. Поиск по индексу
+ Выполняется за o(1) в **ArrayList**.
+ Выполняется в **LinkedList** пропорционально количеству элементов.

#### Вывод
*В большинстве случаев ArrayList на практике оказывается эффективнее LinkedList, исключением является лишь вставка в начало. Соответственно, 
если нужен список, где постоянно происходит вставка в начало, лучше использовать LinkedList, в остальных случаях - ArrayList.*

### Сравнение HashSet, LinkedHashSet, TreeSet
![Альтернативный текст](https://sun9-59.userapi.com/s/v1/if2/M4XNdByVNt0HYO5LxVB8kEIGcKSnitjNx45mxe3XC35OnKBIx40oDRtQ39h3e3csq2VSqcFBwUqBfjH49GUCaecy.jpg?size=874x399&quality=96&type=album)
1. Вставка, удаление, поиск
+ Выполяется в **HashSet** и **LinkedHashSet** за o(1).
+ Выполняетя в **TreeSet** пропорционально количеству элементов.

#### Вывод
*Перечисленные коллекции работают достаточно быстро. HashSet и LinkedHashSet имеют практически одинаковую производительность, в то время как TreeSet работает медленнее из-за необходимости выполнять сортировку при каждой вставке.
Соответственно, если порядок элементов в множестве не важен - лучше использовать HashSet. Если требуется, чтобы элементы хранились в порядке вставки - LinkedHashSet. Если требуется, чтобы элементы множества были отсортированы - TreeSet.*

### Сравнение HashMap, LinkedHashMap, TreeMap
![Альтернативный текст](https://sun9-33.userapi.com/s/v1/if2/gfi51IOHifqXO3OmV6eLALwnO9BrniCxno2oMvnuyfW74hA2taKdYzQgTJC9ieY1NRIQqWdNU_JFUrffM_JyhdaI.jpg?size=874x398&quality=96&type=album)
1. Вставка, удаление, поиск
+ Выполяется в **HashMap** и **LinkedHashMap** за o(1).
+ Выполняетя в **TreeMap** пропорционально количеству элементов.

#### Вывод
*Перечисленные коллекции работают достаточно быстро. HashMap и LinkedHashMap имеют практически одинаковую производительность, в то время как TreeMap работает медленнее из-за необходимости выполнять сортировку при каждой вставке.
Соответственно, если порядок ключей в карте отображения не важен - лучше использовать HashMap. Если требуется, чтобы ключи в карте отображения хранились в порядке вставки - LinkedHashMap. Если требуется, чтобы ключи в карте отображения были отсортированы - TreeMap.*
