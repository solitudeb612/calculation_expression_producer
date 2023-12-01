package com.yyh.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode
@Data
public class NatureNumberExpression implements Expression {
    private List listNumbers;
    private List listOperations;
    private String answer;

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


    @Override
    public String toString() {
        return "NatureNumberExpression{" +
                "listNumbers=" + listNumbers +
                ", listOperations=" + listOperations +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public List getListNumbers() {
        return listNumbers;
    }

    @Override
    public List getListOperations() {
        return listOperations;
    }

    @Override
    public void setListNumbers(List listNumbers) {
        this.listNumbers = listNumbers;
    }

    @Override
    public void setListOperations(List listOperations) {
        this.listOperations = listOperations;
    }

    @Override
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
