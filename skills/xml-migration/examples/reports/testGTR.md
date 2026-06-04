## XML Migration Report

- Files processed : 1
- Total changes   : 59
- ⚠ Warnings      : 18  (semantic replacements — review required)

### examples/testGTR.xml  (38 renames, ⚠ 18 warnings)

    1. [info]    version: 2.0 → 2.8
    2. [info]    namespace: updated (deprecated classes use full FQNs; no spec packages in namespace)
    3. [info]    chainLength: "5000000" → "$(chainLength=5000000)"
    4. [rename]  spec= "FilteredAlignment" → "beast.base.spec.evolution.alignment.FilteredAlignment"
    5. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
    6. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    7. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    8. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    9. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
   10. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
   11. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.SimplexParam"  dropped: lower="0.0", upper="1.0"
   12. [rename]  spec= "RandomTree" → "beast.base.spec.evolution.tree.coalescent.RandomTree"
   13. [rename]  spec= "ConstantPopulation" → "beast.base.spec.evolution.tree.coalescent.ConstantPopulation"
   14. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   15. [rename]  spec= "YuleModel" → "beast.base.spec.evolution.speciation.YuleModel"
   16. [warn] ⚠  Uniform prior upper="Infinity" → "1.0E6" — BEAST3 requires finite bounds; review and adjust this value
   17. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   18. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"  dropped: lower="0.0", upper="5.0"
   19. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   20. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"  dropped: lower="0.0", upper="5.0"
   21. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   22. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"  dropped: lower="0.0", upper="5.0"
   23. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   24. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"  dropped: lower="0.0", upper="5.0"
   25. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   26. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"  dropped: lower="0.0", upper="5.0"
   27. [rename]  spec= "TreeLikelihood" → "beast.base.spec.evolution.likelihood.TreeLikelihood"
   28. [warn] ⚠  TreeLikelihood mapped to spec twin — consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood for multi-core performance
   29. [rename]  spec= "SiteModel" → "beast.base.spec.evolution.sitemodel.SiteModel"
   30. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   31. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   32. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="UnitInterval"  dropped: lower="0.0", upper="1.0"
   33. [rename]  spec= "GTR" → "beast.base.spec.evolution.substitutionmodel.GTR"
   34. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
   35. [rename]  spec= "Frequencies" → "beast.base.spec.evolution.substitutionmodel.Frequencies"
   36. [rename]  spec= "StrictClockModel" → "beast.base.spec.evolution.branchratemodel.StrictClockModel"
   37. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   38. [rename]  spec= "TreeLikelihood" → "beast.base.spec.evolution.likelihood.TreeLikelihood"
   39. [warn] ⚠  TreeLikelihood mapped to spec twin — consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood for multi-core performance
   40. [rename]  spec= "FilteredAlignment" → "beast.base.spec.evolution.alignment.FilteredAlignment"
   41. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   42. [warn] ⚠  spec= "ScaleOperator" [tree=] → "ScaleTreeOperator"  (class split — evolution mode)
   43. [warn] ⚠  spec= "ScaleOperator" [tree=] → "ScaleTreeOperator"  (class split — evolution mode)
   44. [warn] ⚠  spec= "Uniform" [tree=] → full legacy path required  (short name resolves to distribution, not tree operator)
   45. [rename]  spec= "SubtreeSlide" → "beast.base.evolution.operator.kernel.BactrianSubtreeSlide"
   46. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   47. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   48. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   49. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   50. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   51. [rename]  spec= "DeltaExchangeOperator" → "beast.base.spec.inference.operator.DeltaExchangeOperator"
   52. [rename]  spec= "ESS" → "beast.base.spec.inference.util.ESS"
   53. [rename]  spec= "TreeWithMetaDataLogger" → "beast.base.spec.evolution.TreeWithMetaDataLogger"
   54. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   55. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   56. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   57. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   58. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   59. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
