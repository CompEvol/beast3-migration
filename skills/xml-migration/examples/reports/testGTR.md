## XML Migration Report

- Files processed : 1
- Total changes   : 58
- ⚠ Warnings      : 20  (semantic replacements — review required)

### examples/testGTR.xml  (36 renames, ⚠ 20 warnings)

    1. [info]    version: 2.0 → 2.8
    2. [info]    namespace: updated (deprecated classes use full FQNs; no spec packages in namespace)
    3. [rename]  spec= "FilteredAlignment" → "beast.base.spec.evolution.alignment.FilteredAlignment"
    4. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
    5. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    6. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    7. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    8. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    9. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
   10. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.SimplexParam"  dropped: lower="0.0", upper="1.0"
   11. [rename]  spec= "RandomTree" → "beast.base.spec.evolution.tree.coalescent.RandomTree"
   12. [rename]  spec= "ConstantPopulation" → "beast.base.spec.evolution.tree.coalescent.ConstantPopulation"
   13. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   14. [rename]  spec= "YuleModel" → "beast.base.spec.evolution.speciation.YuleModel"
   15. [warn] ⚠  Uniform prior upper="Infinity" → "1.0E6" — BEAST3 requires finite bounds; review and adjust this value
   16. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   17. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0", upper="5.0"
   18. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   19. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0", upper="5.0"
   20. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   21. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0", upper="5.0"
   22. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   23. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0", upper="5.0"
   24. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   25. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0", upper="5.0"
   26. [rename]  spec= "TreeLikelihood" → "beast.base.spec.evolution.likelihood.TreeLikelihood"
   27. [warn] ⚠  TreeLikelihood mapped to spec twin — consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood for multi-core performance
   28. [rename]  spec= "SiteModel" → "beast.base.spec.evolution.sitemodel.SiteModel"
   29. [warn] ⚠  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal" — no lower=/upper= in source; inferred PositiveReal from parameter role "mutationRate". Verify this parameter is genuinely always positive.
   30. [warn] ⚠  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal" — no lower=/upper= in source; inferred PositiveReal from parameter role "gammaShape". Verify this parameter is genuinely always positive.
   31. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="UnitInterval"  dropped: lower="0.0", upper="1.0"
   32. [rename]  spec= "GTR" → "beast.base.spec.evolution.substitutionmodel.GTR"
   33. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
   34. [rename]  spec= "Frequencies" → "beast.base.spec.evolution.substitutionmodel.Frequencies"
   35. [rename]  spec= "StrictClockModel" → "beast.base.spec.evolution.branchratemodel.StrictClockModel"
   36. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   37. [rename]  spec= "TreeLikelihood" → "beast.base.spec.evolution.likelihood.TreeLikelihood"
   38. [warn] ⚠  TreeLikelihood mapped to spec twin — consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood for multi-core performance
   39. [rename]  spec= "FilteredAlignment" → "beast.base.spec.evolution.alignment.FilteredAlignment"
   40. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   41. [warn] ⚠  spec= "ScaleOperator" [tree=] → "ScaleTreeOperator"  (class split — evolution mode)
   42. [warn] ⚠  spec= "ScaleOperator" [tree=] → "ScaleTreeOperator"  (class split — evolution mode)
   43. [warn] ⚠  spec= "Uniform" [tree=] → full legacy path required  (short name resolves to distribution, not tree operator)
   44. [rename]  spec= "SubtreeSlide" → "beast.base.evolution.operator.kernel.BactrianSubtreeSlide"
   45. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   46. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   47. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   48. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   49. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   50. [rename]  spec= "DeltaExchangeOperator" → "beast.base.spec.inference.operator.DeltaExchangeOperator"
   51. [rename]  spec= "ESS" → "beast.base.spec.inference.util.ESS"
   52. [rename]  spec= "TreeWithMetaDataLogger" → "beast.base.spec.evolution.TreeWithMetaDataLogger"
   53. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   54. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   55. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   56. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   57. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
   58. [warn] ⚠  spec= "Prior" → inner distribution inlined  (Prior wrapper removed)
