# Unmapped Beast3 Spec Classes

Scanned from: `~/WorkSpace/beast3`  
Reference:    `~/WorkSpace/beast3-migration/skills/b2xmlb3/deprecated_classes.md`  
Spec classes scanned: **120** — mapped: **59**, unmapped: **61**

Classes in `*.spec.*` packages with no entry in the Replacement column of `deprecated_classes.md`.
Ordered by Maven module then Java package.

## `beast-base` (58 classes)

### `beast.base.spec`

| Class | Full Qualified Name |
|:---|:---|
| `Bounded` | `beast.base.spec.Bounded` |
| `FunctionOfTensor` | `beast.base.spec.FunctionOfTensor` |

### `beast.base.spec.evolution`

| Class | Full Qualified Name |
|:---|:---|
| `IntSum` | `beast.base.spec.evolution.IntSum` |

### `beast.base.spec.evolution.branchratemodel`

| Class | Full Qualified Name |
|:---|:---|
| `Base` | `beast.base.spec.evolution.branchratemodel.Base` |

### `beast.base.spec.evolution.operator`

| Class | Full Qualified Name |
|:---|:---|
| `AdaptableOperatorSampler` | `beast.base.spec.evolution.operator.AdaptableOperatorSampler` |
| `AdaptableVarianceMultivariateNormalOperator` | `beast.base.spec.evolution.operator.AdaptableVarianceMultivariateNormalOperator` |
| `IntervalScaleOperator` | `beast.base.spec.evolution.operator.IntervalScaleOperator` |
| `UpDownOperator` | `beast.base.spec.evolution.operator.UpDownOperator` |

### `beast.base.spec.evolution.substitutionmodel`

| Class | Full Qualified Name |
|:---|:---|
| `Base` | `beast.base.spec.evolution.substitutionmodel.Base` |
| `BasicComplexSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicComplexSubstitutionModel` |
| `BasicGeneralSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicGeneralSubstitutionModel` |
| `BinaryCovarion` | `beast.base.spec.evolution.substitutionmodel.BinaryCovarion` |
| `Blosum62` | `beast.base.spec.evolution.substitutionmodel.Blosum62` |
| `CPREV` | `beast.base.spec.evolution.substitutionmodel.CPREV` |
| `ComplexSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.ComplexSubstitutionModel` |
| `Dayhoff` | `beast.base.spec.evolution.substitutionmodel.Dayhoff` |
| `EmpiricalSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.EmpiricalSubstitutionModel` |
| `GTR` | `beast.base.spec.evolution.substitutionmodel.GTR` |
| `GeneralSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.GeneralSubstitutionModel` |
| `HKY` | `beast.base.spec.evolution.substitutionmodel.HKY` |
| `JTT` | `beast.base.spec.evolution.substitutionmodel.JTT` |
| `JukesCantor` | `beast.base.spec.evolution.substitutionmodel.JukesCantor` |
| `MTREV` | `beast.base.spec.evolution.substitutionmodel.MTREV` |
| `MutationDeathModel` | `beast.base.spec.evolution.substitutionmodel.MutationDeathModel` |
| `SYM` | `beast.base.spec.evolution.substitutionmodel.SYM` |
| `TIM` | `beast.base.spec.evolution.substitutionmodel.TIM` |
| `TN93` | `beast.base.spec.evolution.substitutionmodel.TN93` |
| `TVM` | `beast.base.spec.evolution.substitutionmodel.TVM` |
| `WAG` | `beast.base.spec.evolution.substitutionmodel.WAG` |

### `beast.base.spec.evolution.tree`

| Class | Full Qualified Name |
|:---|:---|
| `SATree` | `beast.base.spec.evolution.tree.SATree` |
| `StrictBinaryTree` | `beast.base.spec.evolution.tree.StrictBinaryTree` |

### `beast.base.spec.inference.distribution`

| Class | Full Qualified Name |
|:---|:---|
| `Bernoulli` | `beast.base.spec.inference.distribution.Bernoulli` |
| `Cauchy` | `beast.base.spec.inference.distribution.Cauchy` |
| `IID` | `beast.base.spec.inference.distribution.IID` |
| `IntUniform` | `beast.base.spec.inference.distribution.IntUniform` |
| `OffsetInt` | `beast.base.spec.inference.distribution.OffsetInt` |
| `OffsetReal` | `beast.base.spec.inference.distribution.OffsetReal` |
| `Prior` | `beast.base.spec.inference.distribution.Prior` |
| `ScalarDistribution` | `beast.base.spec.inference.distribution.ScalarDistribution` |
| `TruncatedInt` | `beast.base.spec.inference.distribution.TruncatedInt` |
| `TruncatedReal` | `beast.base.spec.inference.distribution.TruncatedReal` |

### `beast.base.spec.inference.operator`

| Class | Full Qualified Name |
|:---|:---|
| `AbstractScale` | `beast.base.spec.inference.operator.AbstractScale` |
| `CompoundRealScalarParamHelper` | `beast.base.spec.inference.operator.CompoundRealScalarParamHelper` |
| `Transform` | `beast.base.spec.inference.operator.Transform` |

### `beast.base.spec.inference.operator.uniform`

| Class | Full Qualified Name |
|:---|:---|
| `IntUniformOperator` | `beast.base.spec.inference.operator.uniform.IntUniformOperator` |

### `beast.base.spec.inference.parameter`

| Class | Full Qualified Name |
|:---|:---|
| `BoolScalarParam` | `beast.base.spec.inference.parameter.BoolScalarParam` |
| `BoolVectorParam` | `beast.base.spec.inference.parameter.BoolVectorParam` |
| `IntScalarParam` | `beast.base.spec.inference.parameter.IntScalarParam` |
| `IntSimplexParam` | `beast.base.spec.inference.parameter.IntSimplexParam` |
| `IntVectorParam` | `beast.base.spec.inference.parameter.IntVectorParam` |
| `KeyVectorParam` | `beast.base.spec.inference.parameter.KeyVectorParam` |
| `ParameterUtils` | `beast.base.spec.inference.parameter.ParameterUtils` |
| `RealScalarParam` | `beast.base.spec.inference.parameter.RealScalarParam` |
| `RealVectorParam` | `beast.base.spec.inference.parameter.RealVectorParam` |
| `SimplexParam` | `beast.base.spec.inference.parameter.SimplexParam` |
| `VectorElement` | `beast.base.spec.inference.parameter.VectorElement` |

### `beast.base.spec.inference.util`

| Class | Full Qualified Name |
|:---|:---|
| `AsIntScalar` | `beast.base.spec.inference.util.AsIntScalar` |
| `AsRealScalar` | `beast.base.spec.inference.util.AsRealScalar` |

## `beast-fx` (3 classes)

### `beastfx.app.inputeditor.spec`

| Class | Full Qualified Name |
|:---|:---|
| `ScalarInputEditor` | `beastfx.app.inputeditor.spec.ScalarInputEditor` |
| `SiteModelInputEditor` | `beastfx.app.inputeditor.spec.SiteModelInputEditor` |

### `beastfx.app.methodsection.implementation.spec`

| Class | Full Qualified Name |
|:---|:---|
| `SiteModelMethodsText` | `beastfx.app.methodsection.implementation.spec.SiteModelMethodsText` |
