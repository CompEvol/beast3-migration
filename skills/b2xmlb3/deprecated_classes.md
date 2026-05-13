# Deprecated Classes in Beast3

Scanned from: `~/WorkSpace/beast3`  
Total deprecated classes found: **96**

Entries are ordered by Maven module then Java package.
The **Replacement** column is extracted from the `@deprecated` Javadoc tag;
_no replacement specified_ means the tag was absent.

## `beast-base` (95 classes)

### `beast.base.core`

| Deprecated Class | Replacement |
|:---|:---|
| `Function` | use any of the strong typed classes instead, such as beast.base.spec.type.RealScalar/RealVector beast.base.spec.type.IntScalar/IntVector beast.base.spec.type.BoolScalar/BoolVector with their appropriate domains, e.g. RealScalar<PositiveReal> for positive real scalar function values |

### `beast.base.evolution`

| Deprecated Class | Replacement |
|:---|:---|
| `Sum` | use beast.base.spec.evolution.Sum instead |
| `TreeWithMetaDataLogger` | use beast.base.spec.evolution.TreeWithMetaDataLogger instead |

### `beast.base.evolution.alignment`

| Deprecated Class | Replacement |
|:---|:---|
| `FilteredAlignment` | use `beast.base.spec.evolution.alignment.FilteredAlignment` instead |

### `beast.base.evolution.branchratemodel`

| Deprecated Class | Replacement |
|:---|:---|
| `RandomLocalClockModel` | use beast.base.spec.evolution.branchratemodel.RandomLocalClockModel instead |
| `StrictClockModel` | use beast.base.spec.evolution.branchratemodel.StrictClockModel instead |
| `UCRelaxedClockModel` | use beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel instead |

### `beast.base.evolution.likelihood`

| Deprecated Class | Replacement |
|:---|:---|
| `BeagleTreeLikelihood` | use beast.base.spec.evolution.likelihood.BeagleTreeLikelihood instead |
| `GenericTreeLikelihood` | use beast.base.spec.evolution.likelihood.GenericTreeLikelihood instead |
| `ThreadedTreeLikelihood` | use beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood instead |
| `TreeLikelihood` | use beast.base.spec.evolution.likelihood.TreeLikelihood instead |

### `beast.base.evolution.operator`

| Deprecated Class | Replacement |
|:---|:---|
| `AdaptableOperatorSampler` | _no replacement specified_ |
| `EpochFlexOperator` | _no replacement specified_ |
| `ScaleOperator` | replaced by `beast.base.spec.inference.operator.ScaleOperator`, and `beast.base.spec.evolution.operator.ScaleTreeOperator` |
| `TreeStretchOperator` | _no replacement specified_ |

### `beast.base.evolution.operator.kernel`

| Deprecated Class | Replacement |
|:---|:---|
| `BactrianScaleOperator` | replaced by `beast.base.spec.inference.operator.ScaleOperator`, and `beast.base.spec.evolution.operator.ScaleTreeOperator` |

### `beast.base.evolution.sitemodel`

| Deprecated Class | Replacement |
|:---|:---|
| `SiteModel` | from BEAST v3.0.0 Use beast.base.spec.evolution.sitemodel.SiteModel instead |

### `beast.base.evolution.speciation`

| Deprecated Class | Replacement |
|:---|:---|
| `BirthDeathGernhard08Model` | replaced by `beast.base.spec.evolution.speciation.BirthDeathGernhard08Model` Ported from Beast 1.6 |
| `CalibratedBirthDeathModel` | replaced by `beast.base.spec.evolution.speciation.CalibratedBirthDeathModel` |
| `CalibratedYuleInitialTree` | replaced by `beast.base.spec.evolution.speciation.CalibratedYuleInitialTree` |
| `CalibratedYuleModel` | replaced by `beast.base.spec.evolution.speciation.CalibratedYuleModel` |
| `CalibrationLineagesIterator` | replaced by `beast.base.spec.evolution.speciation.CalibrationLineagesIterator` |
| `CalibrationPoint` | replaced by `beast.base.spec.evolution.speciation.CalibrationPoint` |
| `GeneTreeForSpeciesTreeDistribution` | replaced by `beast.base.spec.evolution.speciation.GeneTreeForSpeciesTreeDistribution` |
| `RandomGeneTree` | replaced by `beast.base.spec.evolution.speciation.RandomGeneTree` |
| `SpeciesTreeLogger` | please use star-beast2 or 3 |
| `SpeciesTreePopFunction` | replaced by `beast.base.spec.evolution.speciation.SpeciesTreePopFunction` |
| `SpeciesTreePrior` | replaced by `beast.base.spec.evolution.speciation.SpeciesTreePrior` |
| `StarBeastStartState` | please use star-beast2 or 3 |
| `YuleModel` | replaced by `beast.base.spec.evolution.speciation.YuleModel` |

### `beast.base.evolution.substitutionmodel`

| Deprecated Class | Replacement |
|:---|:---|
| `BinaryCovarion` | use beast.base.spec.evolution.subsitutionmodel.BinaryCovarion instead |
| `Blosum62` | use beast.base.spec.evolution.subsitutionmodel.Blosum62 instead |
| `CPREV` | use beast.base.spec.evolution.subsitutionmodel.CPREV instead |
| `ComplexSubstitutionModel` | use beast.base.spec.evolution.subsitutionmodel.ComplexSubstitutionModel instead |
| `Dayhoff` | use beast.base.spec.evolution.subsitutionmodel.Dayhoff instead |
| `EmpiricalSubstitutionModel` | use beast.base.spec.evolution.subsitutionmodel.EmpiricalSubstitutionModel instead |
| `Frequencies` | from BEAST v3.0.0 use beast.base.spec.evolution.substitutionmodel.Frequencies instead |
| `GTR` | use beast.base.spec.evolution.subsitutionmodel.GTR instead |
| `GeneralSubstitutionModel` | use beast.base.spec.evolution.subsitutionmodel.GeneralSubstitutionModel instead |
| `HKY` | use beast.base.spec.evolution.subsitutionmodel.HKY instead |
| `JTT` | use beast.base.spec.evolution.subsitutionmodel.JTT instead |
| `JukesCantor` | use beast.base.spec.evolution.subsitutionmodel.JukesCantor instead |
| `MTREV` | use beast.base.spec.evolution.subsitutionmodel.MTREV instead |
| `MutationDeathModel` | use beast.base.spec.evolution.subsitutionmodel.MutationDeathModel instead |
| `SYM` | use beast.base.spec.evolution.subsitutionmodel.SYM instead |
| `TIM` | use beast.base.spec.evolution.subsitutionmodel.TIM instead |
| `TN93` | use beast.base.spec.evolution.subsitutionmodel.TN93 instead |
| `TVM` | use beast.base.spec.evolution.subsitutionmodel.TVM instead |
| `WAG` | use beast.base.spec.evolution.subsitutionmodel.WAG instead |

### `beast.base.evolution.tree`

| Deprecated Class | Replacement |
|:---|:---|
| `ClusterTree` | replaced by `beast.base.spec.evolution.tree.ClusterTree` |
| `MRCAPrior` | replaced by `beast.base.spec.evolution.tree.MRCAPrior` |

### `beast.base.evolution.tree.coalescent`

| Deprecated Class | Replacement |
|:---|:---|
| `BayesianSkyline` | replaced by `beast.base.spec.evolution.tree.coalescent.BayesianSkyline` |
| `CompoundPopulationFunction` | replaced by `beast.base.spec.evolution.tree.coalescent.CompoundPopulationFunction` |
| `ConstantPopulation` | replaced by `beast.base.spec.evolution.tree.coalescent.ConstantPopulation` |
| `ExponentialGrowth` | replaced by `beast.base.spec.evolution.tree.coalescent.ExponentialGrowth` |
| `RandomTree` | replaced by `beast.base.spec.evolution.tree.coalescent.RandomTree` |
| `SampleOffValues` | replaced by `beast.base.spec.inference.operator.SampleOffValues` |
| `ScaledPopulationFunction` | replaced by `beast.base.spec.evolution.tree.coalescent.ScaledPopulationFunction` |

### `beast.base.inference.distribution`

| Deprecated Class | Replacement |
|:---|:---|
| `Beta` | replaced by `beast.base.spec.inference.distribution.Beta` |
| `ChiSquare` | replaced by `beast.base.spec.inference.distribution.ChiSquare` |
| `Dirichlet` | replaced by `beast.base.spec.inference.distribution.Dirichlet` |
| `Exponential` | replaced by `beast.base.spec.inference.distribution.Exponential` |
| `Gamma` | replaced by `beast.base.spec.inference.distribution.Gamma` and `beast.base.spec.inference.distribution.GammaMean` |
| `InverseGamma` | replaced by `beast.base.spec.inference.distribution.InverseGamma` |
| `LaplaceDistribution` | replaced by `beast.base.spec.inference.distribution.Laplace` |
| `LogNormal` | _no replacement specified_ |
| `LogNormalDistributionModel` | replaced by `beast.base.spec.inference.distribution.LogNormal` |
| `MarkovChainDistribution` | use beast.base.spec.inference.distribution.MarkovChainDistribution instead |
| `Normal` | replaced by `beast.base.spec.inference.distribution.Normal` |
| `OneOnX` | use `beast.base.spec.inference.distribution.LogUniform` instead — it has density proportional to 1/x on a bounded support [lower, upper] (lower > 0), which makes it a proper, normalisable replacement for this improper 1/x prior. Set {@code lower}/{@code upper} to the range of plausible values for the quantity being prior'd (e.g. clock rates, population sizes). |
| `ParametricDistribution` | replaced by `beast.base.spec.inference.distribution.TensorDistribution` |
| `Poisson` | replaced by `beast.base.spec.inference.distribution.Poisson` |
| `Prior` | replaced by `beast.base.spec.inference.distribution.TensorDistribution` |
| `Uniform` | replaced by `beast.base.spec.inference.distribution.Uniform` |

### `beast.base.inference.operator`

| Deprecated Class | Replacement |
|:---|:---|
| `BitFlipOperator` | Use beast.base.spec.inference.operator.BitFlipOperator instead. |
| `DeltaExchangeOperator` | Use beast.base.spec.inference.operator.DeltaExchangeOperator instead. |
| `IntRandomWalkOperator` | Use beast.base.spec.inference.operator.IntRandomWalkOperator instead. |
| `IntUniformOperator` | IntUniformOperator is deprecated. Use UniformOperator instead. |
| `RealRandomWalkOperator` | Use beast.base.spec.inference.operator.RealRandomWalkOperator instead. |
| `SwapOperator` | Use beast.base.spec.inference.operator.SwapOperator instead. |
| `UniformOperator` | replaced by `IntUniformOperator`, `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `UpDownOperator` | Use beast.base.spec.inference.operator.UpDownOperator instead. |

### `beast.base.inference.operator.kernel`

| Deprecated Class | Replacement |
|:---|:---|
| `BactrianDeltaExchangeOperator` | from BEAST v3.0.0 use strong typed `RealDeltaExchangeOperator`, `IntDeltaExchangeOperator` |
| `BactrianIntervalOperator` | replaced by `beast.base.spec.inference.operator.uniform.IntervalOperator`. |
| `BactrianRandomWalkOperator` | Use beast.base.spec.inference.operator.RealRandomWalkOperator instead. |
| `BactrianUpDownOperator` | Use beast.base.spec.inference.operator.UpDownOperator instead. |

### `beast.base.inference.parameter`

| Deprecated Class | Replacement |
|:---|:---|
| `BooleanParameter` | use `BoolScalarParam` |
| `CompoundRealParameter` | replaced by `beast.base.spec.inference.parameter.CompoundRealScalarParam` or `beast.base.spec.inference.parameter.CompoundIntScalarParam`. It should be enough, but if not, the new compound could be added as requested. |
| `IntegerParameter` | use `IntScalarParam` or `IntVectorParam` |
| `Parameter` | use `RealScalarParam` or `RealVectorParam` |
| `RealParameter` | use `RealScalarParam` or `RealVectorParam` |

### `beast.base.inference.util`

| Deprecated Class | Replacement |
|:---|:---|
| `ESS` | use `beast.base.spec.inference.util.ESS` |
| `RPNcalculator` | replaced by `beast.base.spec.inference.util.RPNcalculator` A statistic based on evaluating simple expressions. <p> The expressions are in RPN, so no parsing issues. whitespace separated. Variables (other statistics), constants and operations. Currently just the basic four, but easy to extend. |

### `beast.base.spec`

| Deprecated Class | Replacement |
|:---|:---|
| `FunctionOfTensor` | This bridge class will be removed once all code has been adapted to use the strongly-typed tensor API directly. |

### `beast.base.spec.inference.distribution`

| Deprecated Class | Replacement |
|:---|:---|
| `Prior` | Use the specific distribution classes (Normal, LogNormal, etc.) directly. |

## `beast-fx` (1 classes)

### `beastfx.app.beauti`

| Deprecated Class | Replacement |
|:---|:---|
| `PriorInputEditor` | BEAUti editor for the deprecated `beast.base.inference.distribution.Prior` wrapper. In the spec framework distributions take {@code param} directly (no Prior wrapper), so a separate prior editor is no longer needed. |
