(define contain (lambda (l e) (cond ((eq l ()) nil)
                                    ((eq (car l) e) t)
                                    (t (contain (cdr l) e)))))

(define l (quote (a 2 c 4 e 6 g)))
l

(contain l (quote a))
(contain l (quote 1))
(contain l (quote 6))
(contain l (quote 7))
