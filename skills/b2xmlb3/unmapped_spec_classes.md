# Unmapped Beast3 Spec Classes

Scanned from: `~/WorkSpace/beast3`  
Reference:    `~/WorkSpace/beast3-migration/skills/b2xmlb3/deprecated_classes.md`  
Spec classes scanned: **120** — mapped: **87**, unmapped: **33**

Classes in `*.spec.*` packages with no entry in the Replacement column of `deprecated_classes.md`.
Ordered by Maven module then Java package.

## Warnings: Dangling Replacement References

The following FQ names appear in the Replacement column of `deprecated_classes.md` but do not exist as spec classes in the scanned source. This usually indicates a typo or stale package name in the `@deprecated` Javadoc.

| Referenced (non-existent) FQ Name | Cited by Deprecated Class |
|:---|:---|
| `beast.base.spec.type.BoolScalar` | `Function` |
| `beast.base.spec.type.BoolVector` | `Function` |
| `beast.base.spec.type.IntScalar` | `Function` |
| `beast.base.spec.type.IntVector` | `Function` |
| `beast.base.spec.type.RealScalar` | `Function` |
| `beast.base.spec.type.RealVector` | `Function` |
| `beast.base.spec.type.Tensor` | `Parameter` |

## `beast-base` (30 classes)

### `beast.base.spec`

| Class | Full Qualified Name |
|:---|:---|
| `Bounded` | `beast.base.spec.Bounded` |
| `FunctionOfTensor` | `beast.base.spec.FunctionOfTensor` |

### `beast.base.spec.evolution`

| Class | Full Qualified Name |
|:---|:---|
| `IntSum` | `beast.base.spec.evolution.IntSum` |

### `beast.base.spec.evolution.operator`

| Class | Full Qualified Name |
|:---|:---|
| `AdaptableVarianceMultivariateNormalOperator` | `beast.base.spec.evolution.operator.AdaptableVarianceMultivariateNormalOperator` |

### `beast.base.spec.evolution.sitemodel`

| Class | Full Qualified Name |
|:---|:---|
| `SiteModel` | `beast.base.spec.evolution.sitemodel.SiteModel` |

### `beast.base.spec.evolution.substitutionmodel`

| Class | Full Qualified Name |
|:---|:---|
| `BasicComplexSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicComplexSubstitutionModel` |
| `BasicGeneralSubstitutionModel` | `beast.base.spec.evolution.substitutionmodel.BasicGeneralSubstitutionModel` |
| `Frequencies` | `beast.base.spec.evolution.substitutionmodel.Frequencies` |

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

### `beast.base.spec.inference.parameter`

| Class | Full Qualified Name |
|:---|:---|
| `IntSimplexParam` | `beast.base.spec.inference.parameter.IntSimplexParam` |
| `KeyVectorParam` | `beast.base.spec.inference.parameter.KeyVectorParam` |
| `ParameterUtils` | `beast.base.spec.inference.parameter.ParameterUtils` |
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
