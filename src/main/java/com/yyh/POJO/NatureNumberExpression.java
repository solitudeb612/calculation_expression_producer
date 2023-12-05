package com.yyh.POJO;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@EqualsAndHashCode
@Data
public class NatureNumberExpression implements Expression {
    private List listNumbers;
    private List listOperations;
    private String answer;

    private String combinedExpression;



    /**加上@EqualsAndHashCode后再也不用写烦人的hashcode和equals方法了**/
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        NatureNumberExpression that = (NatureNumberExpression) o;
//        if (listOperationsAreSimple(that.listOperations)) {
//            return listsContainSameElements(listNumbers, that.listNumbers);
//        }
//        else {
//            return Objects.equals(listNumbers, that.listNumbers) &&
//                    Objects.equals(listOperations, that.listOperations) &&
//                    Objects.equals(answer, that.answer) &&
//                    listsAreEqual(listNumbers, that.listNumbers) &&
//                    listsAreEqual(listOperations, that.listOperations);
//        }
//
//    }
//
//    private boolean listsContainSameElements(List<Integer> list1, List<Integer> list2) {
//        if (list1 == null && list2 == null) {
//            return true;
//        }
//        if (list1 == null || list2 == null || list1.size() != list2.size()) {
//            return false;
//        }
//        for (Integer num : list1) {
//            if (!list2.contains(num)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    private boolean listOperationsAreSimple(List<String> list) {
//        for (String operation : list) {
//            if (!("+".equals(operation) || "*".equals(operation))) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private boolean listsAreEqual(List<?> list1, List<?> list2) {
//        if (list1 == null && list2 == null) {
//            return true;
//        }
//        if (list1 == null || list2 == null || list1.size() != list2.size()) {
//            return false;
//        }
//        for (int i = 0; i < list1.size(); i++) {
//            if (!Objects.equals(list1.get(i), list2.get(i))) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 17;
//        result = 31 * result + (listNumbers != null ? listNumbers.hashCode() : 0);
//        result = 31 * result + (listOperations != null ? listOperations.hashCode() : 0);
//        result = 31 * result + (answer != null ? answer.hashCode() : 0);
//        return result;
//    }

    /**
     * 虽然加了@EqualsAndHashCode，还是需要重写equals方法，因为@EqualsAndHashCode认为1+2和2+1是不同的式子
     * @param o
     * @return
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NatureNumberExpression)) return false;
        NatureNumberExpression that = (NatureNumberExpression) o;
        return unorderedListEquals(listNumbers, that.listNumbers) &&
                unorderedListEquals(listOperations, that.listOperations) &&
                Objects.equals(answer, that.answer);
//                Objects.equals(combinedExpression, that.combinedExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unorderedListHashCode(listNumbers), unorderedListHashCode(listOperations), answer);
    }

    private boolean unorderedListEquals(List<String> list1, List<String> list2) {
        if (list1 == null || list2 == null) {
            return Objects.equals(list1, list2);
        }
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    private int unorderedListHashCode(List<String> list) {
        if (list == null) {
            return 0;
        }
        return new HashSet<>(list).hashCode();
    }


}
