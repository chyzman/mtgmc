package com.chyzman.mtgmc.util;

import io.wispforest.endec.Deserializer;
import io.wispforest.endec.SerializationContext;
import io.wispforest.endec.Serializer;
import io.wispforest.endec.StructEndec;
import io.wispforest.endec.impl.StructField;

public class ExtraEndecs {
    public static <S, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19> StructEndec<S> of(
            StructField<S, F1> f1,
            StructField<S, F2> f2,
            StructField<S, F3> f3,
            StructField<S, F4> f4,
            StructField<S, F5> f5,
            StructField<S, F6> f6,
            StructField<S, F7> f7,
            StructField<S, F8> f8,
            StructField<S, F9> f9,
            StructField<S, F10> f10,
            StructField<S, F11> f11,
            StructField<S, F12> f12,
            StructField<S, F13> f13,
            StructField<S, F14> f14,
            StructField<S, F15> f15,
            StructField<S, F16> f16,
            StructField<S, F17> f17,
            StructField<S, F18> f18,
            StructField<S, F19> f19,
            Function19<F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, S> constructor
    ) {
        return new StructEndec<>() {
            @Override
            public void encodeStruct(SerializationContext ctx, Serializer<?> serializer, Serializer.Struct struct, S value) {
                f1.encodeField(ctx, serializer, struct, value);
                f2.encodeField(ctx, serializer, struct, value);
                f3.encodeField(ctx, serializer, struct, value);
                f4.encodeField(ctx, serializer, struct, value);
                f5.encodeField(ctx, serializer, struct, value);
                f6.encodeField(ctx, serializer, struct, value);
                f7.encodeField(ctx, serializer, struct, value);
                f8.encodeField(ctx, serializer, struct, value);
                f9.encodeField(ctx, serializer, struct, value);
                f10.encodeField(ctx, serializer, struct, value);
                f11.encodeField(ctx, serializer, struct, value);
                f12.encodeField(ctx, serializer, struct, value);
                f13.encodeField(ctx, serializer, struct, value);
                f14.encodeField(ctx, serializer, struct, value);
                f15.encodeField(ctx, serializer, struct, value);
                f16.encodeField(ctx, serializer, struct, value);
                f17.encodeField(ctx, serializer, struct, value);
                f18.encodeField(ctx, serializer, struct, value);
                f19.encodeField(ctx, serializer, struct, value);
            }

            @Override
            public S decodeStruct(SerializationContext ctx, Deserializer<?> deserializer, Deserializer.Struct struct) {
                return constructor.apply(
                        f1.decodeField(ctx, deserializer, struct),
                        f2.decodeField(ctx, deserializer, struct),
                        f3.decodeField(ctx, deserializer, struct),
                        f4.decodeField(ctx, deserializer, struct),
                        f5.decodeField(ctx, deserializer, struct),
                        f6.decodeField(ctx, deserializer, struct),
                        f7.decodeField(ctx, deserializer, struct),
                        f8.decodeField(ctx, deserializer, struct),
                        f9.decodeField(ctx, deserializer, struct),
                        f10.decodeField(ctx, deserializer, struct),
                        f11.decodeField(ctx, deserializer, struct),
                        f12.decodeField(ctx, deserializer, struct),
                        f13.decodeField(ctx, deserializer, struct),
                        f14.decodeField(ctx, deserializer, struct),
                        f15.decodeField(ctx, deserializer, struct),
                        f16.decodeField(ctx, deserializer, struct),
                        f17.decodeField(ctx, deserializer, struct),
                        f18.decodeField(ctx, deserializer, struct),
                        f19.decodeField(ctx, deserializer, struct)
                );
            }
        };
    }

    public static <S, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22> StructEndec<S> of(
            StructField<S, F1> f1,
            StructField<S, F2> f2,
            StructField<S, F3> f3,
            StructField<S, F4> f4,
            StructField<S, F5> f5,
            StructField<S, F6> f6,
            StructField<S, F7> f7,
            StructField<S, F8> f8,
            StructField<S, F9> f9,
            StructField<S, F10> f10,
            StructField<S, F11> f11,
            StructField<S, F12> f12,
            StructField<S, F13> f13,
            StructField<S, F14> f14,
            StructField<S, F15> f15,
            StructField<S, F16> f16,
            StructField<S, F17> f17,
            StructField<S, F18> f18,
            StructField<S, F19> f19,
            StructField<S, F20> f20,
            StructField<S, F21> f21,
            StructField<S, F22> f22,
            Function22<F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, S> constructor
    ) {
        return new StructEndec<>() {
            @Override
            public void encodeStruct(SerializationContext ctx, Serializer<?> serializer, Serializer.Struct struct, S value) {
                f1.encodeField(ctx, serializer, struct, value);
                f2.encodeField(ctx, serializer, struct, value);
                f3.encodeField(ctx, serializer, struct, value);
                f4.encodeField(ctx, serializer, struct, value);
                f5.encodeField(ctx, serializer, struct, value);
                f6.encodeField(ctx, serializer, struct, value);
                f7.encodeField(ctx, serializer, struct, value);
                f8.encodeField(ctx, serializer, struct, value);
                f9.encodeField(ctx, serializer, struct, value);
                f10.encodeField(ctx, serializer, struct, value);
                f11.encodeField(ctx, serializer, struct, value);
                f12.encodeField(ctx, serializer, struct, value);
                f13.encodeField(ctx, serializer, struct, value);
                f14.encodeField(ctx, serializer, struct, value);
                f15.encodeField(ctx, serializer, struct, value);
                f16.encodeField(ctx, serializer, struct, value);
                f17.encodeField(ctx, serializer, struct, value);
                f18.encodeField(ctx, serializer, struct, value);
                f19.encodeField(ctx, serializer, struct, value);
                f20.encodeField(ctx, serializer, struct, value);
                f21.encodeField(ctx, serializer, struct, value);
                f22.encodeField(ctx, serializer, struct, value);
            }

            @Override
            public S decodeStruct(SerializationContext ctx, Deserializer<?> deserializer, Deserializer.Struct struct) {
                return constructor.apply(
                        f1.decodeField(ctx, deserializer, struct),
                        f2.decodeField(ctx, deserializer, struct),
                        f3.decodeField(ctx, deserializer, struct),
                        f4.decodeField(ctx, deserializer, struct),
                        f5.decodeField(ctx, deserializer, struct),
                        f6.decodeField(ctx, deserializer, struct),
                        f7.decodeField(ctx, deserializer, struct),
                        f8.decodeField(ctx, deserializer, struct),
                        f9.decodeField(ctx, deserializer, struct),
                        f10.decodeField(ctx, deserializer, struct),
                        f11.decodeField(ctx, deserializer, struct),
                        f12.decodeField(ctx, deserializer, struct),
                        f13.decodeField(ctx, deserializer, struct),
                        f14.decodeField(ctx, deserializer, struct),
                        f15.decodeField(ctx, deserializer, struct),
                        f16.decodeField(ctx, deserializer, struct),
                        f17.decodeField(ctx, deserializer, struct),
                        f18.decodeField(ctx, deserializer, struct),
                        f19.decodeField(ctx, deserializer, struct),
                        f20.decodeField(ctx, deserializer, struct),
                        f21.decodeField(ctx, deserializer, struct),
                        f22.decodeField(ctx, deserializer, struct)
                );
            }
        };
    }

    public static <S, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27, F28, F29, F30, F31, F32, F33, F34, F35, F36, F37, F38, F39, F40, F41, F42, F43, F44, F45> StructEndec<S> of(
            StructField<S, F1> f1,
            StructField<S, F2> f2,
            StructField<S, F3> f3,
            StructField<S, F4> f4,
            StructField<S, F5> f5,
            StructField<S, F6> f6,
            StructField<S, F7> f7,
            StructField<S, F8> f8,
            StructField<S, F9> f9,
            StructField<S, F10> f10,
            StructField<S, F11> f11,
            StructField<S, F12> f12,
            StructField<S, F13> f13,
            StructField<S, F14> f14,
            StructField<S, F15> f15,
            StructField<S, F16> f16,
            StructField<S, F17> f17,
            StructField<S, F18> f18,
            StructField<S, F19> f19,
            StructField<S, F20> f20,
            StructField<S, F21> f21,
            StructField<S, F22> f22,
            StructField<S, F23> f23,
            StructField<S, F24> f24,
            StructField<S, F25> f25,
            StructField<S, F26> f26,
            StructField<S, F27> f27,
            StructField<S, F28> f28,
            StructField<S, F29> f29,
            StructField<S, F30> f30,
            StructField<S, F31> f31,
            StructField<S, F32> f32,
            StructField<S, F33> f33,
            StructField<S, F34> f34,
            StructField<S, F35> f35,
            StructField<S, F36> f36,
            StructField<S, F37> f37,
            StructField<S, F38> f38,
            StructField<S, F39> f39,
            StructField<S, F40> f40,
            StructField<S, F41> f41,
            StructField<S, F42> f42,
            StructField<S, F43> f43,
            StructField<S, F44> f44,
            StructField<S, F45> f45,
            Function45<F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21, F22, F23, F24, F25, F26, F27, F28, F29, F30, F31, F32, F33, F34, F35, F36, F37, F38, F39, F40, F41, F42, F43, F44, F45, S> constructor
    ) {
        return new StructEndec<>() {
            @Override
            public void encodeStruct(SerializationContext ctx, Serializer<?> serializer, Serializer.Struct struct, S value) {
                f1.encodeField(ctx, serializer, struct, value);
                f2.encodeField(ctx, serializer, struct, value);
                f3.encodeField(ctx, serializer, struct, value);
                f4.encodeField(ctx, serializer, struct, value);
                f5.encodeField(ctx, serializer, struct, value);
                f6.encodeField(ctx, serializer, struct, value);
                f7.encodeField(ctx, serializer, struct, value);
                f8.encodeField(ctx, serializer, struct, value);
                f9.encodeField(ctx, serializer, struct, value);
                f10.encodeField(ctx, serializer, struct, value);
                f11.encodeField(ctx, serializer, struct, value);
                f12.encodeField(ctx, serializer, struct, value);
                f13.encodeField(ctx, serializer, struct, value);
                f14.encodeField(ctx, serializer, struct, value);
                f15.encodeField(ctx, serializer, struct, value);
                f16.encodeField(ctx, serializer, struct, value);
                f17.encodeField(ctx, serializer, struct, value);
                f18.encodeField(ctx, serializer, struct, value);
                f19.encodeField(ctx, serializer, struct, value);
                f20.encodeField(ctx, serializer, struct, value);
                f21.encodeField(ctx, serializer, struct, value);
                f22.encodeField(ctx, serializer, struct, value);
                f23.encodeField(ctx, serializer, struct, value);
                f24.encodeField(ctx, serializer, struct, value);
                f25.encodeField(ctx, serializer, struct, value);
                f26.encodeField(ctx, serializer, struct, value);
                f27.encodeField(ctx, serializer, struct, value);
                f28.encodeField(ctx, serializer, struct, value);
                f29.encodeField(ctx, serializer, struct, value);
                f30.encodeField(ctx, serializer, struct, value);
                f31.encodeField(ctx, serializer, struct, value);
                f32.encodeField(ctx, serializer, struct, value);
                f33.encodeField(ctx, serializer, struct, value);
                f34.encodeField(ctx, serializer, struct, value);
                f35.encodeField(ctx, serializer, struct, value);
                f36.encodeField(ctx, serializer, struct, value);
                f37.encodeField(ctx, serializer, struct, value);
                f38.encodeField(ctx, serializer, struct, value);
                f39.encodeField(ctx, serializer, struct, value);
                f40.encodeField(ctx, serializer, struct, value);
                f41.encodeField(ctx, serializer, struct, value);
                f42.encodeField(ctx, serializer, struct, value);
                f43.encodeField(ctx, serializer, struct, value);
                f44.encodeField(ctx, serializer, struct, value);
                f45.encodeField(ctx, serializer, struct, value);
            }

            @Override
            public S decodeStruct(SerializationContext ctx, Deserializer<?> deserializer, Deserializer.Struct struct) {
                return constructor.apply(
                        f1.decodeField(ctx, deserializer, struct),
                        f2.decodeField(ctx, deserializer, struct),
                        f3.decodeField(ctx, deserializer, struct),
                        f4.decodeField(ctx, deserializer, struct),
                        f5.decodeField(ctx, deserializer, struct),
                        f6.decodeField(ctx, deserializer, struct),
                        f7.decodeField(ctx, deserializer, struct),
                        f8.decodeField(ctx, deserializer, struct),
                        f9.decodeField(ctx, deserializer, struct),
                        f10.decodeField(ctx, deserializer, struct),
                        f11.decodeField(ctx, deserializer, struct),
                        f12.decodeField(ctx, deserializer, struct),
                        f13.decodeField(ctx, deserializer, struct),
                        f14.decodeField(ctx, deserializer, struct),
                        f15.decodeField(ctx, deserializer, struct),
                        f16.decodeField(ctx, deserializer, struct),
                        f17.decodeField(ctx, deserializer, struct),
                        f18.decodeField(ctx, deserializer, struct),
                        f19.decodeField(ctx, deserializer, struct),
                        f20.decodeField(ctx, deserializer, struct),
                        f21.decodeField(ctx, deserializer, struct),
                        f22.decodeField(ctx, deserializer, struct),
                        f23.decodeField(ctx, deserializer, struct),
                        f24.decodeField(ctx, deserializer, struct),
                        f25.decodeField(ctx, deserializer, struct),
                        f26.decodeField(ctx, deserializer, struct),
                        f27.decodeField(ctx, deserializer, struct),
                        f28.decodeField(ctx, deserializer, struct),
                        f29.decodeField(ctx, deserializer, struct),
                        f30.decodeField(ctx, deserializer, struct),
                        f31.decodeField(ctx, deserializer, struct),
                        f32.decodeField(ctx, deserializer, struct),
                        f33.decodeField(ctx, deserializer, struct),
                        f34.decodeField(ctx, deserializer, struct),
                        f35.decodeField(ctx, deserializer, struct),
                        f36.decodeField(ctx, deserializer, struct),
                        f37.decodeField(ctx, deserializer, struct),
                        f38.decodeField(ctx, deserializer, struct),
                        f39.decodeField(ctx, deserializer, struct),
                        f40.decodeField(ctx, deserializer, struct),
                        f41.decodeField(ctx, deserializer, struct),
                        f42.decodeField(ctx, deserializer, struct),
                        f43.decodeField(ctx, deserializer, struct),
                        f44.decodeField(ctx, deserializer, struct),
                        f45.decodeField(ctx, deserializer, struct)
                );
            }
        };
    }

    public interface Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19);
    }

    public interface Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22);
    }

    //God help me
    public interface Function45<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32, T33, T34, T35, T36, T37, T38, T39, T40, T41, T42, T43, T44, T45, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13, T14 t14, T15 t15, T16 t16, T17 t17, T18 t18, T19 t19, T20 t20, T21 t21, T22 t22, T23 t23, T24 t24, T25 t25, T26 t26, T27 t27, T28 t28, T29 t29, T30 t30, T31 t31, T32 t32, T33 t33, T34 t34, T35 t35, T36 t36, T37 t37, T38 t38, T39 t39, T40 t40, T41 t41, T42 t42, T43 t43, T44 t44, T45 t45);
    }
}
