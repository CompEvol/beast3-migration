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
| `Function` | use any of the strong typed classes instead, such as `beast.base.spec.type.RealScalar` / `beast.base.spec.type.RealVector`, `beast.base.spec.type.IntScalar` / `beast.base.spec.type.IntVector`, `beast.base.spec.type.BoolScalar` / `beast.base.spec.type.BoolVector` with their appropriate domains |

### `beast.base.evolution`

| Deprecated Class | Replacement |
|:---|:---|
| `Sum` | use `beast.base.spec.evolution.Sum` instead |
| `TreeWithMetaDataLogger` | use `beast.base.spec.evolution.TreeWithMetaDataLogger` instead |

### `beast.base.evolution.alignment`

| Deprecated Class | Replacement |
|:---|:---|
| `FilteredAlignment` | use `beast.base.spec.evolution.alignment.FilteredAlignment` instead |

### `beast.base.evolution.branchratemodel`

| Deprecated Class | Replacement |
|:---|:---|
| `Base` | replaced by `beast.base.spec.evolution.branchratemodel.Base` |
| `RandomLocalClockModel` | use `beast.base.spec.evolution.branchratemodel.RandomLocalClockModel` instead |
| `StrictClockModel` | use `beast.base.spec.evolution.branchratemodel.StrictClockModel` instead |
| `UCRelaxedClockModel` | use `beast.base.spec.evolution.branchratemodel.UCRelaxedClockModel` instead |

### `beast.base.evolution.likelihood`

| Deprecated Class | Replacement |
|:---|:---|
| `BeagleTreeLikelihood` | use `beast.base.spec.evolution.likelihood.BeagleTreeLikelihood` instead |
| `GenericTreeLikelihood` | use `beast.base.spec.evolution.likelihood.GenericTreeLikelihood` instead |
| `ThreadedTreeLikelihood` | use `beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood` instead |
| `TreeLikelihood` | use `beast.base.spec.evolution.likelihood.TreeLikelihood` instead |

### `beast.base.evolution.operator`

| Deprecated Class | Replacement |
|:---|:---|
| `AdaptableOperatorSampler` | use `beast.base.spec.evolution.operator.AdaptableOperatorSampler` instead |
| `EpochFlexOperator` | use `beast.base.spec.evolution.operator.IntervalScaleOperator` instead |
| `ScaleOperator` | replaced by `beast.base.spec.inference.operator.ScaleOperator`, and `beast.base.spec.evolution.operator.ScaleTreeOperator` |
| `TreeStretchOperator` | use `beast.base.spec.evolution.operator.IntervalScaleOperator` instead |

### `beast.base.evolution.operator.kernel`

| Deprecated Class | Replacement |
|:---|:---|
| `BactrianScaleOperator` | replaced by `beast.base.spec.inference.operator.ScaleOperator`, and `beast.base.spec.evolution.operator.ScaleTreeOperator` |

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
| `Base` | use `beast.base.spec.evolution.substitutionmodel.Base` instead |
| `BinaryCovarion` | use `beast.base.spec.evolution.substitutionmodel.BinaryCovarion` instead |
| `Blosum62` | use `beast.base.spec.evolution.substitutionmodel.Blosum62` instead |
| `CPREV` | use `beast.base.spec.evolution.substitutionmodel.CPREV` instead |
| `ComplexSubstitutionModel` | use `beast.base.spec.evolution.substitutionmodel.ComplexSubstitutionModel` instead |
| `Dayhoff` | use `beast.base.spec.evolution.substitutionmodel.Dayhoff` instead |
| `EmpiricalSubstitutionModel` | use `beast.base.spec.evolution.substitutionmodel.EmpiricalSubstitutionModel` instead |
| `GTR` | use `beast.base.spec.evolution.substitutionmodel.GTR` instead |
| `GeneralSubstitutionModel` | use `beast.base.spec.evolution.substitutionmodel.GeneralSubstitutionModel` instead |
| `HKY` | use `beast.base.spec.evolution.substitutionmodel.HKY` instead |
| `JTT` | use `beast.base.spec.evolution.substitutionmodel.JTT` instead |
| `JukesCantor` | use `beast.base.spec.evolution.substitutionmodel.JukesCantor` instead |
| `MTREV` | use `beast.base.spec.evolution.substitutionmodel.MTREV` instead |
| `MutationDeathModel` | use `beast.base.spec.evolution.substitutionmodel.MutationDeathModel` instead |
| `NucleotideBase` | since no class uses this -- copy code if you really need this |
| `SYM` | use `beast.base.spec.evolution.substitutionmodel.SYM` instead |
| `TIM` | use `beast.base.spec.evolution.substitutionmodel.TIM` instead |
| `TN93` | use `beast.base.spec.evolution.substitutionmodel.TN93` instead |
| `TVM` | use `beast.base.spec.evolution.substitutionmodel.TVM` instead |
| `WAG` | use `beast.base.spec.evolution.substitutionmodel.WAG` instead |

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
| `LogNormal` | replaced by `beast.base.spec.inference.distribution.LogNormal` |
| `LogNormalDistributionModel` | replaced by `beast.base.spec.inference.distribution.LogNormal` |
| `MarkovChainDistribution` | use `beast.base.spec.inference.distribution.MarkovChainDistribution` instead |
| `Normal` | replaced by `beast.base.spec.inference.distribution.Normal` |
| `OneOnX` | use `beast.base.spec.inference.distribution.LogUniform` instead — it has density proportional to 1/x on a bounded support [lower, upper] (lower > 0), which makes it a proper, normalisable replacement for this improper 1/x prior. Set {@code lower}/{@code upper} to the range of plausible values for the quantity being prior'd (e.g. clock rates, population sizes). |
| `ParametricDistribution` | replaced by `beast.base.spec.inference.distribution.TensorDistribution` |
| `Poisson` | replaced by `beast.base.spec.inference.distribution.Poisson` |
| `Prior` | replaced by `beast.base.spec.inference.distribution.TensorDistribution` |
| `Uniform` | replaced by `beast.base.spec.inference.distribution.Uniform` |

### `beast.base.inference.operator`

| Deprecated Class | Replacement |
|:---|:---|
| `BitFlipOperator` | Use `beast.base.spec.inference.operator.BitFlipOperator` instead. |
| `DeltaExchangeOperator` | Use `beast.base.spec.inference.operator.DeltaExchangeOperator` instead. |
| `IntRandomWalkOperator` | Use `beast.base.spec.inference.operator.IntRandomWalkOperator` instead. |
| `IntUniformOperator` | IntUniformOperator is deprecated. Use `beast.base.spec.inference.operator.uniform.IntUniformOperator` instead. |
| `RealRandomWalkOperator` | Use `beast.base.spec.inference.operator.RealRandomWalkOperator` instead. |
| `SwapOperator` | Use `beast.base.spec.inference.operator.SwapOperator` instead. |
| `UniformOperator` | replaced by `beast.base.spec.inference.operator.uniform.IntUniformOperator`, `beast.base.spec.inference.operator.uniform.IntervalOperator` |
| `UpDownOperator` | Use `beast.base.spec.evolution.operator.UpDownOperator` instead. |

### `beast.base.inference.operator.kernel`

| Deprecated Class | Replacement |
|:---|:---|
| `BactrianIntervalOperator` | replaced by `beast.base.spec.inference.operator.uniform.IntervalOperator`. |
| `BactrianRandomWalkOperator` | Use `beast.base.spec.inference.operator.RealRandomWalkOperator` instead. |
| `BactrianUpDownOperator` | Use `beast.base.spec.evolution.operator.UpDownOperator` instead. |

### `beast.base.inference.parameter`

| Deprecated Class | Replacement |
|:---|:---|
| `BooleanParameter` | use `beast.base.spec.inference.parameter.BoolScalarParam` or `beast.base.spec.inference.parameter.BoolVectorParam` |
| `CompoundRealParameter` | replaced by `beast.base.spec.inference.parameter.CompoundRealScalarParam` or `beast.base.spec.inference.parameter.CompoundIntScalarParam`. It should be enough, but if not, the new compound could be added as requested. |
| `IntegerParameter` | use `beast.base.spec.inference.parameter.IntScalarParam` or `beast.base.spec.inference.parameter.IntVectorParam` |
| `Parameter` | use `beast.base.spec.type.Tensor` if read only, alternatively use `RealScalarParam` or `RealVectorParam` having setters. |
| `RealParameter` | use `beast.base.spec.inference.parameter.RealScalarParam` or `beast.base.spec.inference.parameter.RealVectorParam` |

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
