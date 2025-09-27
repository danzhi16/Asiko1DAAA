# Divide-and-Conquer Algorithms: Analysis and Metrics

## Architecture Notes
This project implements classic divide-and-conquer algorithms (MergeSort, QuickSort, Deterministic Select, Closest Pair 2D) with safe recursion patterns. Recursion depth and allocations are tracked using dedicated metrics classes. All algorithms are instrumented to collect:
- Execution time
- Recursion depth
- Operation counters (comparisons, swaps, allocations)
- Memory allocations

Metrics are exported in CSV format for analysis.

## Algorithms & Recurrence Analysis

### 1. MergeSort
- **Recurrence:** T(n) = 2T(n/2) + O(n)
- **Master Theorem (Case 2):** a=2, b=2, f(n)=O(n)
- **Solution:** T(n) = Θ(n log n)
- **Proof:**
  - By Master Theorem: If f(n) = Θ(n^log_b a), then T(n) = Θ(n log n).
  - Here, log_2 2 = 1, so f(n) = Θ(n^1) = Θ(n).

### 2. QuickSort
- **Recurrence (average):** T(n) = 2T(n/2) + O(n)
- **Master Theorem (Case 2):** a=2, b=2, f(n)=O(n)
- **Solution:** T(n) = Θ(n log n)
- **Proof:**
  - Same as MergeSort for average case.
  - Worst case: T(n) = T(n-1) + O(n) ⇒ T(n) = Θ(n^2)

### 3. Deterministic Select (Median-of-Medians)
- **Recurrence:** T(n) ≤ T(n/5) + T(7n/10) + O(n)
- **Akra-Bazzi Theorem:**
  - General form: T(n) = Σ a_i T(b_i n) + f(n)
  - Here, a_1=1, b_1=1/5; a_2=1, b_2=7/10; f(n)=O(n)
- **Solution:** T(n) = Θ(n)
- **Proof:**
  - Akra-Bazzi yields linear time for this recurrence.

### 4. Closest Pair of Points (2D)
- **Recurrence:** T(n) = 2T(n/2) + O(n)
- **Master Theorem (Case 2):** a=2, b=2, f(n)=O(n)
- **Solution:** T(n) = Θ(n log n)
- **Proof:**
  - Same as MergeSort.

## Plots & Discussion
- **Plots:**
  - Time vs n
  - Depth vs n
- **Constant-factor effects:**
  - Cache locality, JVM garbage collection, and buffer reuse impact constant factors in running time and memory usage.

## Summary
Theoretical results (Θ(n log n) for sorting/closest pair, Θ(n) for select) align with measured metrics. Minor mismatches arise due to implementation details and hardware effects.

## CSV Format
Results are exported as:
```
algorithmName;runTime;counter;depth;allocation;...
```
Each metric is separated by a semicolon for compatibility with spreadsheet tools.

## References
- Cormen, Leiserson, Rivest, Stein. Introduction to Algorithms.
- Akra-Bazzi Theorem: https://en.wikipedia.org/wiki/Akra%E2%80%93Bazzi_method
- Master Theorem: https://en.wikipedia.org/wiki/Master_theorem_(analysis_of_algorithms)

---
Date: September 27, 2025
Author: Nursultan Khaimuldin

