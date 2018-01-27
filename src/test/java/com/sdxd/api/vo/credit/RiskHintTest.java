package com.sdxd.api.vo.credit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import java.util.*;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.vo.credit
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 17/3/30     melvin                 Created
 */
public class RiskHintTest {

//    @Test
//    public void testExtract() {
//        String content = "[{\"value\":[\"001\", \"002\"],\"valueType\":\"userId\"},{\"value\":[\"003\", \"002\"],\"valueType\":\"userId\"}]";
//        RiskHint hint = new RiskHint(null);
//        Assert.assertArrayEquals(
//                new String[]{"001", "002", "003"},
//                hint.getUserIds().toArray(new String[hint.getUserIds().size()]));
//    }

//    @Test
//    public void testPartition() {
//        Double[] n = new Double[]{525.0,550.0,550.0,550.0,1100.0,1100.0,550.0,550.0,550.0,1100.0,550.0,550.0,1100.0,550.0,1100.0,525.0,550.0,525.0,550.0,550.0,550.0,525.0};
//        Arrays.sort(n, Collections.reverseOrder());
//        System.out.println(Lists.newArrayList(n));
//        LinkedList<Double> nn = Lists.newLinkedList(Lists.newArrayList(n));
//
//        List<Tuple3<Integer, Double, Integer>> e = Lists.newArrayList();
//        int p = 6;
//        int last = p - 1;
//        Map<Integer, List<Double>> m = Maps.newHashMap();
//
//        for (int i = 0; i < p; i++) {
//            Double f = nn.removeFirst();
//
//            List<Double> d = m.get(i);
//            if (d == null) {
//                d = Lists.newArrayList();
//                m.put(i, d);
//            }
//            d.add(f);
//
//            e.add(new Tuple3<>(i, f, 1));
//        }
//
//        System.out.println("first: " + e);
//
//        e.addAll(nn.stream().map(v -> new Tuple3<>(0, v, 0)).collect(Collectors.toList()));
//        nn.clear();
//        while (e.size() != p) {
//            Collections.sort(e, (o1, o2) -> {
//                if (o1 == null) {
//                    return 1;
//                }
//                if (o2 == null) {
//                    return -1;
//                }
//                if (o1.v2() < o2.v2()) {
//                    return 1;
//                }
//                if (o1.v2() > o2.v2()) {
//                    return -1;
//                }
//                if (o1.v2().doubleValue() == o2.v2().doubleValue()) {
//                    return 0;
//                }
//                return 0;
//            });
//            Tuple3<Integer, Double, Integer> t = e.get(last);
//            Tuple3<Integer, Double, Integer> tt = e.remove(p);
//            t = new Tuple3<>(t.v1, t.v2 + tt.v2, t.v3 + 1);
//            e.set(last, t);
//            List<Double> d = m.get(t.v1);
//            d.add(tt.v2);
//        }
//
//        System.out.println(m);
//        System.out.println(e);
////        for (Map.Entry<Integer, List<Double>> entry : m.entrySet()) {
////            Integer k = entry.getKey();
////            List<Double> v = entry.getValue();
////            Double all = v.stream().reduce(0d, (a1, a2) -> a1 + a2, (a1, a2) -> a1);
////            System.out.printf("i: %d, c: %d, a: %f\n", k, v.size(), all);
////        }
//    }

    @Test
    public void testPartition1() {
        Double[] n = new Double[]{525.0,550.0,550.0,550.0,1100.0,1100.0,550.0,550.0,550.0,1100.0,550.0,550.0,1100.0,550.0,1100.0,525.0,550.0,525.0,550.0,550.0,550.0,525.0};
        Arrays.sort(n, Collections.reverseOrder());
        LinkedList<Double> nn = Lists.newLinkedList(Lists.newArrayList(n));
        System.out.println(Lists.newArrayList(n));

        int p = 6;
        int last = p - 1;
        Map<Integer, List<Double>> m = Maps.newHashMap();
        Map<Integer, Tuple2<Double, Integer>> e = Maps.newHashMap();
        boolean sw = false;
        while (nn.size() > 0) {
            int j = sw ? last : 0;
            for (int i = 0; i < p; i++) {
                List<Double> d = m.get(j);
                if (d == null) {
                    d = Lists.newArrayList();
                    m.put(i, d);
                }

                if (nn.size() > 0) {
                    Double v = nn.removeFirst();
                    d.add(v);
                    Tuple2<Double, Integer> t = e.get(j);
                    t = t == null ? new Tuple2<>(v, 1) : new Tuple2<>(t.v1 + v, t.v2 + 1);
                    e.put(j, t);
                }
                j = sw ? j - 1 : j + 1;
                if (i == last) {
                    sw = !sw;
                }
            }
        }

        System.out.println(m);
        System.out.println(e);
    }
}
