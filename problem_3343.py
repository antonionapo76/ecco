from __future__ import annotations

from typing import List


MOD = 1_000_000_007


class Solution:
    def countBalancedPermutations(self, num: str) -> int:
        """
        Count distinct permutations where digit sums at even and odd indices are equal.
        """
        counts = [0] * 10
        total_sum = 0
        for ch in num:
            digit = ord(ch) - ord("0")
            counts[digit] += 1
            total_sum += digit

        if total_sum % 2 != 0:
            return 0

        n = len(num)
        even_slots = (n + 1) // 2
        odd_slots = n // 2
        target = total_sum // 2

        facts, inv_facts = self._factorials(n)

        # dp[used_slots][used_sum] stores:
        # sum over choices so far of Π inv_fact[x_d] * inv_fact[count_d - x_d]
        dp = [[0] * (target + 1) for _ in range(even_slots + 1)]
        dp[0][0] = 1

        for digit, count in enumerate(counts):
            if count == 0:
                continue

            next_dp = [[0] * (target + 1) for _ in range(even_slots + 1)]
            contrib = [inv_facts[k] * inv_facts[count - k] % MOD for k in range(count + 1)]

            for used_slots in range(even_slots + 1):
                for used_sum in range(target + 1):
                    current = dp[used_slots][used_sum]
                    if current == 0:
                        continue

                    max_pick = min(count, even_slots - used_slots)
                    for pick in range(max_pick + 1):
                        new_sum = used_sum + digit * pick
                        if new_sum > target:
                            break
                        next_dp[used_slots + pick][new_sum] = (
                            next_dp[used_slots + pick][new_sum]
                            + current * contrib[pick]
                        ) % MOD

            dp = next_dp

        weighted_ways = dp[even_slots][target]
        return weighted_ways * facts[even_slots] % MOD * facts[odd_slots] % MOD

    def _factorials(self, n: int) -> tuple[List[int], List[int]]:
        facts = [1] * (n + 1)
        for i in range(1, n + 1):
            facts[i] = facts[i - 1] * i % MOD

        inv_facts = [1] * (n + 1)
        inv_facts[n] = pow(facts[n], MOD - 2, MOD)
        for i in range(n, 0, -1):
            inv_facts[i - 1] = inv_facts[i] * i % MOD

        return facts, inv_facts
