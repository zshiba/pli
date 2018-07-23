(define update (lambda (k v l) (cond ((eq k ()) l)
                                     ((eq l ()) l)
                                     ((eq k (car (car l))) (cons (cons k (cons v ())) (cdr l)))
                                     (t (cons (car l) (update k v (cdr l)))))))

(define pairs (quote ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))))
pairs

(update (quote k1) (quote vvv111) pairs)

(update (quote k2) (quote vvv222) pairs)

(update (quote k5) (quote vvv555) pairs)

(update (quote something) (quote something) pairs)

(define updated_pairs (update (quote k3) (quote vvv333) pairs))
updated_pairs
